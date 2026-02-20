package com.mlog.yiji.esdemo1.service.impl;

import com.mlog.yiji.esdemo1.service.GeoService;
import com.mlog.yiji.esdemo1.service.WeatherService;
import com.mlog.yiji.esdemo1.util.QueryLevelMappingUtil;
import com.mlog.yiji.esdemo1.util.ThreadPoolUtil;
import com.mlog.yiji.esdemo1.vo.GeoVo;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoUtils;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static com.mlog.yiji.esdemo1.util.QueryLevelMappingUtil.LEVEL_DISTRICT;

@Service
public class GeoServiceImpl implements GeoService {

   private final RestHighLevelClient restHighLevelClient;
   private final WeatherService weatherService;

   public GeoServiceImpl(RestHighLevelClient restHighLevelClient,
                         WeatherService weatherService)
   {
      this.restHighLevelClient = restHighLevelClient;
      this.weatherService = weatherService;
   }

   @Override
   public List<GeoVo> searchGeoBoundingBox(Double zoom,
                                           Double maxLat, Double minLon,
                                           Double minLat, Double maxLon)
      throws IOException
   {
      // normalize lon and lat. 190 lon === -170 lon
      minLon = GeoUtils.normalizeLon(minLon);
      maxLon = GeoUtils.normalizeLon(maxLon);
      minLat = GeoUtils.normalizeLat(minLat);
      maxLat = GeoUtils.normalizeLat(maxLat);

      ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
         2, 4, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
         Executors.defaultThreadFactory(),
         new ThreadPoolExecutor.DiscardOldestPolicy());

      Future<SearchResponse> districtResponse = searchGeo(threadPoolExecutor,
         zoom, DISTRICT_INDEX_NAME, DISTRICT_TYPE, maxLat, minLon, minLat, maxLon, false);
      Future<SearchResponse> globalResponse = searchGeo(threadPoolExecutor,
         zoom, GLOBAL_INDEX_NAME, GLOBAL_TYPE, maxLat, minLon, minLat, maxLon, true);

      List<Future<SearchResponse>> response = Arrays.asList(districtResponse, globalResponse);

      List<GeoVo> geoVos = mergeResponseHints(response);

      threadPoolExecutor.shutdown();

      return geoVos;
   }

   private List<GeoVo> mergeResponseHints(List<Future<SearchResponse>> hits)
   {
      if(hits == null) {
         return null;
      }

//      long start = System.currentTimeMillis();

      List<SearchHit> totalHits = hits.parallelStream()
         .map(hit -> {
            try {
//               long start = System.currentTimeMillis();
               SearchResponse searchResponse = hit.get(50, TimeUnit.SECONDS);

               SearchHit[] searchHits = searchResponse != null && searchResponse.getHits() != null
                  ? searchResponse.getHits().getHits() : null;

//               LOGGER.info("Query es data {} seconds. hints count is: {}",
//                  (System.currentTimeMillis() - start) * 1.0 / 1000,
//                  searchHits != null ? searchHits.length : 0);

               return searchHits;
            } catch (Exception e) {
               e.printStackTrace();
            }

            return null;
         })
         .filter(hs -> hs != null && hs.length > 0)
         .map(Arrays::stream)
         .flatMap(hit -> hit)
         .collect(Collectors.toList());

      int nthread = ThreadPoolUtil.getThreadNumber(totalHits.size());
      int count = (int) Math.ceil(totalHits.size() * 1.0 / nthread);

      ExecutorService executors = new ThreadPoolExecutor(nthread, nthread,
         0L, TimeUnit.MILLISECONDS,
         new LinkedBlockingQueue<>(),
         new ThreadPoolExecutor.CallerRunsPolicy());

      SearchHit[] searchHits = totalHits.toArray(new SearchHit[0]);

      List<Future<List<GeoVo>>> result = new ArrayList<>();

      for(int i = 0; i < nthread; i++) {
         List<SearchHit> list = ThreadPoolUtil.getThreadHits(i, count, searchHits);

         Future<List<GeoVo>> future =
            executors.submit(() ->  buildGeoVo(list));

         result.add(future);
      }
//      LOGGER.info("Query http data {} seconds.",
//         (System.currentTimeMillis() - start) * 1.0 / 1000);

      List<GeoVo> geoVos = convertGeoVO(result);

      executors.shutdown();

      return geoVos;

   }

   private List<GeoVo> convertGeoVO(List<Future<List<GeoVo>>> result) {
      if(result ==  null) {
         return null;
      }

      Vector<GeoVo> vos = new Vector<>();

      result.parallelStream()
         .map(listFuture -> {
            try {
               List<GeoVo> geoVos = listFuture.get(60, TimeUnit.SECONDS);

               return geoVos != null ? geoVos.stream() : null;
            } catch (Exception e) {
               e.printStackTrace();
            }

            return null;
         })
         .filter(Objects::nonNull)
         .flatMap(geoVo -> geoVo)
         .forEach(vos::add);

      return vos;
   }

   private List<GeoVo> buildGeoVo(List<SearchHit> hits) {
      if(hits == null || hits.size() < 1) {
         return null;
      }

      List<GeoVo> geos = new ArrayList<>();

      for(SearchHit hit : hits) {
         Map<String, Object> sourceAsMap = hit.getSourceAsMap();
         GeoVo geoVo = new GeoVo();

         String areaCode = (String) sourceAsMap.get("areacode");
         geoVo.setAreaCode(areaCode);

         if((Boolean) sourceAsMap.get(LEVEL_DISTRICT)) {
            String region = (String) sourceAsMap.get("country");

            if(ObjectUtils.isEmpty(region)) {
               // china
               geoVo.setAreaName((String) sourceAsMap.get("district"));
            }
            else {
               // global
               geoVo.setAreaName(region);
            }
         }
         else {
            geoVo.setAreaName((String) sourceAsMap.get("city"));
         }

         HashMap<String, Double> location
            = (HashMap<String, Double>) sourceAsMap.get("location");
         Double lon = location.get("lon");
         Double lat = location.get("lat");

         geoVo.setLon(lon);
         geoVo.setLat(lat);
         geoVo.setRealTimeCode(weatherService.getRealtimeCode(areaCode, lon, lat));

         geos.add(geoVo);
      }

      return geos;
   }

   private Future<SearchResponse> searchGeo(ThreadPoolExecutor threadPoolExecutor,
                                            Double zoom, String index,
                                            String type, Double top, Double left,
                                            Double bottom, Double right,
                                            boolean global)
      throws IOException
   {
      return threadPoolExecutor.submit(() -> {
         GeoBoundingBoxQueryBuilder queryBuilder = QueryBuilders
            .geoBoundingBoxQuery("location")
            .setCorners(top, left, bottom, right);

         // 通过SearchSourceBuilder构建搜索参数
         SearchSourceBuilder builder = new SearchSourceBuilder();
         // 设置query参数，绑定前面创建的Query对象
         builder.query(queryBuilder);
         builder.size(Integer.MAX_VALUE);

         QueryBuilder postFilter = QueryLevelMappingUtil.getPostFilter(zoom, global);

         if(postFilter != null) {
            builder.postFilter(postFilter);
         }

         SearchRequest searchRequest = new SearchRequest();
         // 设置SearchRequest搜索参数
         searchRequest.source(builder);
         searchRequest.indices(index);
         searchRequest.types(type);

         return restHighLevelClient.search(
            searchRequest, RequestOptions.DEFAULT);
      });
   }

   private static final Logger LOGGER = LoggerFactory.getLogger(GeoServiceImpl.class);
}
