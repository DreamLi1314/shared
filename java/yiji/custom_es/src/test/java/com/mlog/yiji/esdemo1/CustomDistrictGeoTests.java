package com.mlog.yiji.esdemo1;

import com.csvreader.CsvReader;
import com.mlog.yiji.esdemo1.enums.QueryLevel;
import com.mlog.yiji.esdemo1.service.GeoService;
import com.mlog.yiji.esdemo1.vo.GeoVo;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.rest.RestStatus;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.mlog.yiji.esdemo1.util.QueryLevelMappingUtil.*;

@Disabled
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomDistrictGeoTests {

   private static final String CUSTOM_INDEX_NAME = GeoService.DISTRICT_INDEX_NAME;
   private static final String CUSTOM_TYPE = GeoService.DISTRICT_TYPE;

   @Autowired
   private RestHighLevelClient restHighLevelClient;

   @Autowired
   private GeoService geoService;

   @Test
   @Order(1)
   public void testCreateIndex() throws Exception {
      CreateIndexRequest request = new CreateIndexRequest(CUSTOM_INDEX_NAME);

      request.settings(Settings.builder().put("max_result_window", Integer.MAX_VALUE));

      buildIndexMapping(request);

      restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
   }

   @Test
   @Order(2)
   public void putCustomDataToEs() throws Exception {
      ClassPathResource resource = new ClassPathResource("district_20210207.csv");

      Assertions.assertTrue(resource.exists(), "File is not found.");

      InputStream inputStream = resource.getInputStream();
      CsvReader csvReader = new CsvReader(inputStream, StandardCharsets.UTF_8);

      String[] title = new String[0];

      if(csvReader.readHeaders()) {
         title = csvReader.getHeaders();
      }

      boolean capital = true;

      while(csvReader.readRecord()){
         insertGeo(csvReader, title, capital);
         capital = false;
      }

      csvReader.close();
   }

   @Test
   @Order(3)
   public void testCustomQueryGeo() throws IOException {
      List<GeoVo> response = geoService.searchGeoBoundingBox(
         QueryLevel.CAPITAL, 90D, -180D, -90D, 180D);

      Assertions.assertNotNull(response, "Query geo response is null");

      for(int i = 0; i < response.size(); i++) {
         GeoVo geo  =  response.get(i);

         if(geo.toString().contains("北京")) {
            LOGGER.info("Success!");
         }
      }

      LOGGER.info("Query geo count: {}", response.size());
   }

   private void insertGeo(CsvReader csvReader, String[] title, boolean capital) throws IOException {
      Map<String, Object> source = new HashMap<>();

      for(int i = 0; i < title.length; i++) {
         String key = title[i];
         String value = csvReader.get(i);

         if(key.contains("lon")) {
            int lonIndex = key.indexOf("lon");

            if(lonIndex == 0) {
               key = "location";
            }
            else {
               key = key.substring(0, lonIndex) + "location";
            }

            double lon = Double.parseDouble(value);
            double lat = Double.parseDouble(csvReader.get(i + 1));
            i++; // skip lat that in next position

            source.put(key, new GeoPoint(lat, lon));
         }
         else {
            source.put(key, value);
         }
      }

      QueryLevel queryLevel;

      if("1".equals(source.get("exclude"))) {
         String districtCode = (String) source.get("districtcode");

         if(capital) {
            queryLevel = QueryLevel.CAPITAL;
         }
         else if((districtCode).endsWith("0100")
            // 香港(810000)/澳门(820010)特别行政区, 台湾省(710113)
            || "810000".equals(districtCode)
            || "820010".equals(districtCode)
            || "710113".equals(districtCode))
         {
            queryLevel = QueryLevel.PROVINCE_CAPITAL;
         }
         else {
            queryLevel = QueryLevel.CITY;
         }
      }
      else {
         queryLevel = QueryLevel.DISTRICT;
      }

      source.put(CAPITAL, queryLevel == QueryLevel.CAPITAL);
      source.put(PROVINCE_CAPITAL,
         (queryLevel.getLevel() & QueryLevel.PROVINCE_CAPITAL.getLevel()) != 0);
      source.put(LEVEL_CITY,
         (queryLevel.getLevel() & QueryLevel.CITY.getLevel()) != 0);
      source.put(LEVEL_DISTRICT,
         (queryLevel.getLevel() & QueryLevel.DISTRICT.getLevel()) != 0);

      IndexRequest request = new IndexRequest(CUSTOM_INDEX_NAME)
         .type(CUSTOM_TYPE)
         .id(UUID.randomUUID().toString())
         .source(source);

      IndexResponse indexResponse = null;
      boolean conflict = false;

      try {
         // sync
         indexResponse = restHighLevelClient.index(
            request, RequestOptions.DEFAULT);
      }
      catch(ElasticsearchException e) {
         if(e.status() == RestStatus.CONFLICT) {
            LOGGER.warn("This id {} is exists.", request.id(), e);
            conflict = true;
         }
         else {
            throw e;
         }
      }

      LOGGER.info("Init Geo Data response: {}", indexResponse);

      Assertions.assertTrue(indexResponse != null || conflict, "Init Geo data error.");
   }

   private void buildIndexMapping(CreateIndexRequest request) throws IOException {
      XContentBuilder mappingBuilder = JsonXContent.contentBuilder()
         .startObject()
            .startObject("properties")
               .startObject("areacode")
                  .field("type", "long")
               .endObject()

               .startObject("districtcode")
                  .field("type", "long")
               .endObject()

               .startObject("city_geocode")
                  .field("type", "long")
               .endObject()

               .startObject("city")
                  .field("type", "keyword")
               .endObject()

               .startObject("district_geocode")
                  .field("type", "long")
               .endObject()

               .startObject("district")
                  .field("type", "keyword")
               .endObject()

               .startObject("location")
                  .field("type", "geo_point")
                  .field("index", "true")
               .endObject()

               .startObject("sta_fc")
                  .field("type", "keyword")
               .endObject()

               .startObject("sta_rt")
                  .field("type", "keyword")
               .endObject()

               .startObject("province")
                  .field("type", "keyword")
               .endObject()

               .startObject("fc_location")
                  .field("type", "geo_point")
               .endObject()

               .startObject("rt_location")
                  .field("type", "geo_point")
               .endObject()

               .startObject("origin_areacode")
                  .field("type", "long")
               .endObject()

               .startObject("exclude")
                  .field("type", "long")
               .endObject()

               .startObject("province_geocode")
                  .field("type", "long")
               .endObject()

               .startObject("districtEn")
                  .field("type", "keyword")
               .endObject()

               .startObject("districtPy")
                  .field("type", "keyword")
               .endObject()

               .startObject(CAPITAL)
                  .field("type", "boolean")
                  .field("index", "true")
               .endObject()

               .startObject(PROVINCE_CAPITAL)
                  .field("type", "boolean")
                  .field("index", "true")
               .endObject()

               .startObject(LEVEL_CITY)
                  .field("type", "boolean")
                  .field("index", "true")
               .endObject()

               .startObject(LEVEL_DISTRICT)
                  .field("type", "boolean")
                  .field("index", "true")
               .endObject()
            .endObject()
         .endObject();

      request.mapping(CUSTOM_TYPE, mappingBuilder);
   }


   private static final Logger LOGGER = LoggerFactory.getLogger(CustomDistrictGeoTests.class);
}
