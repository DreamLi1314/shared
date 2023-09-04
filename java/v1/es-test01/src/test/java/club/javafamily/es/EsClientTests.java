package club.javafamily.es;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.*;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jack Li
 * @date 2023/4/11 下午4:59
 * @description
 */
public class EsClientTests {

   private static RestClient restClient;
   private static ObjectMapper objectMapper = new ObjectMapper();

   @BeforeAll
   public static void init() {
      final RestClientBuilder clientBuilder = RestClient.builder(
         new HttpHost("106.75.70.77", 9200, "http"));

      clientBuilder.setFailureListener(new RestClient.FailureListener(){
         @Override
         public void onFailure(Node node) {
            System.out.println("出错了: " + node);
         }
      });

      restClient = clientBuilder.build();
   }

   @AfterAll
   public static void destroy() throws IOException {
      restClient.close();
   }

   @Test
   void testGetInfo() throws Exception {
      Request request = new Request("GET", "/_cluster/state");
      request.addParameter("pretty", "true");
      final Response response = restClient.performRequest(request);

      System.out.println(response.getStatusLine());
      System.out.println(EntityUtils.toString(response.getEntity()));
   }

   @Test
   void testGetDoc() throws Exception {
      Request request = new Request("GET", "/haoke/_doc/TC2Hb4cB91qK6Ubr8e4R");
      request.addParameter("pretty", "true");
      final Response response = restClient.performRequest(request);

      System.out.println(response.getStatusLine());
      System.out.println(EntityUtils.toString(response.getEntity()));
   }

   @Test
   void testCreate() throws Exception {
      Request request = new Request("POST", "/haoke/_doc");

      Map<String, Object> data = new HashMap<>();
      data.put("id", "2001");
      data.put("title", "张江高科");
      data.put("price", "3500");

      request.setJsonEntity(objectMapper.writeValueAsString(data));
      final Response response = restClient.performRequest(request);

      System.out.println(response.getStatusLine());
      System.out.println(EntityUtils.toString(response.getEntity()));
   }

   @Test
   void testSearch() throws Exception {
      Request request = new Request("POST", "/haoke/_doc/_search");
      String searchJson = "{\n" +
         "  \"query\": {\n" +
         "    \"match\": {\n" +
         "      \"title\": \"拎包入住\"\n" +
         "    }\n" +
         "  }\n" +
         "}";
      request.setJsonEntity(searchJson);
      request.addParameter("pretty", "true");
      final Response response = restClient.performRequest(request);

      System.out.println(response.getStatusLine());

      final String json = EntityUtils.toString(response.getEntity());

      final JsonNode jsonNode = objectMapper.readTree(json);
      final int total = jsonNode.get("hits").get("total").asInt();
      System.out.println(total);

      final ArrayNode hits = (ArrayNode) jsonNode.get("hits").get("hits");

      for (JsonNode hit : hits) {
         System.out.println(hit.get("_source").get("title").asText());
      }
   }
}
