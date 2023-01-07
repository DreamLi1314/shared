package com.mlog.yiji.global.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ApplicationEventListener implements ApplicationListener<ContextClosedEvent> {
   @Override
   public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
      if(restHighLevelClient != null) {
         try {
            restHighLevelClient.close();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }

   public ApplicationEventListener(RestHighLevelClient restHighLevelClient) {
      this.restHighLevelClient = restHighLevelClient;
   }

   private final RestHighLevelClient restHighLevelClient;
}
