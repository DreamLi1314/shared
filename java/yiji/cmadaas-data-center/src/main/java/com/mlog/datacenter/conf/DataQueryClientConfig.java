package com.mlog.datacenter.conf;

import cma.music.client.DataQueryClient;
import com.mlog.datacenter.properties.CmadaasProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataQueryClientConfig {

   private final CmadaasProperties cmadaasProperties;

   public DataQueryClientConfig(CmadaasProperties cmadaasProperties) {
      this.cmadaasProperties = cmadaasProperties;
   }

   @Bean
   public DataQueryClient dataQueryClient() throws Exception {
      DataQueryClient dataQueryClient = new DataQueryClient();
      dataQueryClient.initResources();

      return dataQueryClient;
   }
}
