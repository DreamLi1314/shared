package com.inetsoft.test;

import com.inetsoft.test.bean.TestProp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplicationTests {

   @Autowired
   private TestProp testProp;

   @Test
   public void contextLoads() {
      System.out.println("==========testProp====" + testProp.getVersion());

      System.out.println("=========null====" + Boolean.parseBoolean(null));
      System.out.println("=========''====" + Boolean.parseBoolean(""));
   }

}
