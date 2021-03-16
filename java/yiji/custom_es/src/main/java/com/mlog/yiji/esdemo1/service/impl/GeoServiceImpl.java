package com.mlog.yiji.esdemo1.service.impl;

import com.mlog.yiji.esdemo1.enums.QueryLevel;
import com.mlog.yiji.esdemo1.service.GeoService;
import com.mlog.yiji.esdemo1.service.WeatherService;
import com.mlog.yiji.esdemo1.util.QueryLevelMappingUtil;
import com.mlog.yiji.esdemo1.vo.GeoVo;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

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
   public List<GeoVo> searchGeoBoundingBox(QueryLevel queryLevel,
                                           Double top, Double left, Double bottom, Double right)
      throws IOException
   {
      SearchResponse districtResponse = searchGeo(
         queryLevel, DISTRICT_INDEX_NAME, DISTRICT_TYPE, top, left, bottom, right);
      SearchResponse globalResponse = searchGeo(
         queryLevel, GLOBAL_INDEX_NAME, GLOBAL_TYPE, top, left, bottom, right);

      return mergeResponseHints(districtResponse, globalResponse);
   }

   private List<GeoVo> mergeResponseHints(SearchResponse districtResponse,
                                          SearchResponse globalResponse)
   {
      if(districtResponse == null && globalResponse == null) {
         return null;
      }

      SearchHit[] hits = districtResponse != null && districtResponse.getHits() != null
         ? districtResponse.getHits().getHits()
         : new SearchHit[0];
      SearchHit[] globalHints = globalResponse != null && globalResponse.getHits() != null
         ? globalResponse.getHits().getHits()
         : new SearchHit[0];
      List<GeoVo> geos = new ArrayList<>();

      buildGeoVo(hits, geos);
      buildGeoVo(globalHints, geos);

      return geos;

   }

   private void buildGeoVo(SearchHit[] hits, List<GeoVo> geos) {
      if(hits == null || hits.length < 1) {
         return;
      }

      for(SearchHit hint : hits) {
         Map<String, Object> sourceAsMap = hint.getSourceAsMap();
         GeoVo geoVo = new GeoVo();

         String areaCode = (String) sourceAsMap.get("areacode");
         geoVo.setAreaCode(areaCode);

         if((Boolean) sourceAsMap.get(LEVEL_DISTRICT)) {
            geoVo.setAreaName((String) sourceAsMap.get("district"));
         }
         else {
            geoVo.setAreaName((String) sourceAsMap.get("city"));
         }

         HashMap<String, Double> location
            = (HashMap<String, Double>) sourceAsMap.get("location");

         geoVo.setLon(location.get("lon"));
         geoVo.setLat(location.get("lat"));
         geoVo.setRealTimeCode(weatherService.getRealtimeCode(areaCode));

         geos.add(geoVo);
      }
   }

   private SearchResponse searchGeo(QueryLevel queryLevel, String index,
                                    String type, Double top, Double left,
                                    Double bottom, Double right)
      throws IOException
   {
      if(queryLevel == null) {
         queryLevel = QueryLevel.CAPITAL;
      }

      GeoBoundingBoxQueryBuilder queryBuilder = QueryBuilders
         .geoBoundingBoxQuery("location")
         .setCorners(top, left, bottom, right);

      // 通过SearchSourceBuilder构建搜索参数
      SearchSourceBuilder builder = new SearchSourceBuilder();
      // 设置query参数，绑定前面创建的Query对象
      builder.query(queryBuilder);
      builder.size(Integer.MAX_VALUE);

      QueryBuilder postFilter = QueryLevelMappingUtil.getPostFilter(queryLevel);

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
   }
}
