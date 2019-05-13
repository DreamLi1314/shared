package com.inetsoft.test.bean;

import com.inetsoft.test.util.TestEnv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource(value = "file:E:/shared/java/v1/test/bin/test.properties")
@Component
public class TestProp {

   @Value("${inetsoft.asset.version}")
   private String version;

   @Value("#{envBean.path}")
   private String test;


   public String getVersion() {
      return version;
   }

   public void setVersion(String version) {
      this.version = version;
   }
}
