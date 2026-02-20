package com.mlog.test01;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class Test01ApplicationTests {

   @Test
   void contextLoads() {
      List<Integer> data = new ArrayList<>();

      for (int i = 0; i <= 30; i+=1) {
         data.add(i);
      }

      System.out.println(data);
      System.out.println(data.size());
   }

}
