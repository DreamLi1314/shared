package com.mlog.yiji.esdemo1.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EsConfig {

   @Value("${es.port}")
   private int port;

   @Value("${es.host}")
   private String host;

   @Bean
   public RestHighLevelClient restHighLevelClient() {
      RestHighLevelClient client = new RestHighLevelClient(
         RestClient.builder(new HttpHost(host, port)));

      return client;
   }

}
