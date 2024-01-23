package com.geoviswtx;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

/**
 * @author Jack Li
 * @date 2023/11/19 下午2:13
 * @description
 */
@SpringBootTest
public class ContextTests {

   @Autowired
   private ApplicationContext applicationContext;

   @Test
   void test() {
      Assertions.assertNotNull(applicationContext, "服务启动异常了!");
   }

}
