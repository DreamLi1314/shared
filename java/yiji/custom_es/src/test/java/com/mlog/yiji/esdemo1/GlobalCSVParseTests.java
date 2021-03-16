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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GlobalCSVParseTests {

   private static final String CUSTOM_INDEX_NAME = GeoService.GLOBAL_INDEX_NAME;
   private static final String CUSTOM_TYPE = GeoService.GLOBAL_TYPE;

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

   @ParameterizedTest
   @ValueSource(strings = "global_20200304.csv")
   @Order(2)
   public void testPutCustomDataToEs(String filePath) throws Exception {
      ClassPathResource resource = new ClassPathResource(filePath);

      Assertions.assertTrue(resource.exists(), "File is not found.");

      InputStream inputStream = resource.getInputStream();

      CsvReader csvReader = new CsvReader(inputStream, StandardCharsets.UTF_8);

      String[] title = new String[0];

      if(csvReader.readHeaders()) {
         title = csvReader.getHeaders();
      }

      while(csvReader.readRecord()){
         insertGeo(csvReader, title);
      }

      csvReader.close();
   }

   @Test
   @Order(3)
   public void testCustomQueryGeo() throws IOException {
      List<GeoVo> response = geoService.searchGeoBoundingBox(
         QueryLevel.DISTRICT, 90D, -180D, -90D, 180D);

      Assertions.assertNotNull(response, "Query geo response is null");

      LOGGER.info("Query geo count: {}", response.size());
   }

   private void insertGeo(CsvReader csvReader, String[] title) throws IOException {
      Map<String, Object> source = new HashMap<>();
      QueryLevel queryLevel = null;

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

      if("1".equals(source.get("capital"))) {
         queryLevel = QueryLevel.CAPITAL;
      }
      else if(((String) source.get("areacode")).endsWith("01001")) {
         queryLevel = QueryLevel.PROVINCE_CAPITAL;
      }
      else {
         queryLevel = QueryLevel.CITY;
      }

      source.put(CAPITAL, queryLevel == QueryLevel.CAPITAL);
      source.put(PROVINCE_CAPITAL,
         (queryLevel.getLevel() & QueryLevel.PROVINCE_CAPITAL.getLevel()) == QueryLevel.PROVINCE_CAPITAL.getLevel());
      source.put(LEVEL_CITY,
         (queryLevel.getLevel() & QueryLevel.CITY.getLevel()) == QueryLevel.CITY.getLevel());
      source.put(LEVEL_DISTRICT,
         (queryLevel.getLevel() & QueryLevel.DISTRICT.getLevel()) == QueryLevel.DISTRICT.getLevel());

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
                  .field("type", "keyword")
               .endObject()

               .startObject("nation")
                  .field("type", "keyword")
               .endObject()

               .startObject("province")
                  .field("type", "keyword")
               .endObject()

               .startObject("city")
                  .field("type", "keyword")
               .endObject()

               .startObject("country")
                  .field("type", "keyword")
               .endObject()

               .startObject("location")
                  .field("type", "geo_point")
                  .field("index", "true")
               .endObject()

               .startObject("nation_en")
                  .field("type", "keyword")
               .endObject()

               .startObject("province_en")
                  .field("type", "keyword")
               .endObject()

               .startObject("city_en")
                  .field("type", "keyword")
               .endObject()

               .startObject("country_en")
                  .field("type", "keyword")
               .endObject()

               .startObject("capital")
                  .field("type", "long")
               .endObject()

               .startObject("timezone")
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


   private static final Logger LOGGER = LoggerFactory.getLogger(GlobalCSVParseTests.class);
}
