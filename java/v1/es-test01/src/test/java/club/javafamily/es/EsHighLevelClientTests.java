package club.javafamily.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Jack Li
 * @date 2023/4/11 下午4:59
 * @description
 */
public class EsHighLevelClientTests {

   private static RestHighLevelClient restClient;
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

      restClient = new RestHighLevelClient(clientBuilder);
   }

   @AfterAll
   public static void destroy() throws IOException {
      restClient.close();
   }

//   @Test
//   void testGetInfo() throws Exception {
//      Request request = new Request("GET", "/_cluster/state");
//      request.addParameter("pretty", "true");
//      final Response response = restClient.performRequest(request);
//
//      System.out.println(response.getStatusLine());
//      System.out.println(EntityUtils.toString(response.getEntity()));
//   }

//   @Test
//   void testGetDoc() throws Exception {
//      Request request = new Request("GET", "/haoke/_doc/TC2Hb4cB91qK6Ubr8e4R");
//      request.addParameter("pretty", "true");
//      final Response response = restClient.performRequest(request);
//
//      System.out.println(response.getStatusLine());
//      System.out.println(EntityUtils.toString(response.getEntity()));
//   }

   @Test
   void testCreate() throws Exception {
      Map<String, Object> data = new HashMap<>();
      data.put("id", "2002");
      data.put("title", "南京西路 拎包入住 一室一厅");
      data.put("price", "4500");

      IndexRequest request = new IndexRequest("haoke")
         .source(data);

      final IndexResponse response
         = restClient.index(request, RequestOptions.DEFAULT);

      System.out.println(response.getIndex());
      System.out.println(response.getType());
      System.out.println(response.getId());
      System.out.println(response.getVersion());
      System.out.println(response.getResult());
      System.out.println(response.getShardInfo());
   }


   @Test
   void testCreateAsync() throws Exception {
      Map<String, Object> data = new HashMap<>();
      data.put("id", "2003");
      data.put("title", "南京东路 拎包入住 三室一厅");
      data.put("price", "8500");

      IndexRequest request = new IndexRequest("haoke")
         .source(data);

      restClient.indexAsync(request, RequestOptions.DEFAULT, new ActionListener<IndexResponse>() {
         @Override
         public void onResponse(IndexResponse response) {
            System.out.println(response.getIndex());
            System.out.println(response.getType());
            System.out.println(response.getId());
            System.out.println(response.getVersion());
            System.out.println(response.getResult());
            System.out.println(response.getShardInfo());
         }

         @Override
         public void onFailure(Exception e) {
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());
         }
      });

      System.out.println("ok");

      TimeUnit.SECONDS.sleep(2);
   }

   @Test
   void testSearch() throws Exception {
      SearchRequest request = new SearchRequest("haoke");
      SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
      sourceBuilder.query(QueryBuilders.matchQuery("title", "拎包入住"));
      sourceBuilder.from(1);
      sourceBuilder.size(1);
      sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

      request.source(sourceBuilder);

      final SearchResponse response = restClient.search(request, RequestOptions.DEFAULT);

      final int total = response.getTotalShards();
      System.out.println(total);

      final SearchHits hits = response.getHits();

      for (SearchHit hit : hits) {
         final Map<String, Object> map = hit.getSourceAsMap();
         System.out.println(map);
      }
   }
}
