package com.mlog.datacenter;

import cma.music.client.DataQueryClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CmadaasDataCenterApplicationTests {

   @Autowired
   private DataQueryClient client;

   @Test
   void testContext() {
      Assertions.assertNotNull(client, "init data query client error!");
   }

}
