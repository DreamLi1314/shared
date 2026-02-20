package com.inetsoft.test.test02;

import com.inetsoft.test.dao.Test2Repository;
import com.inetsoft.test.domain.Test2Entity;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Jack Li
 * @date 2021/7/31 10:58 上午
 * @description
 */
@SpringBootTest
public class InitTest2Table {

   @Autowired
   private Test2Repository repository;

   @Test
   @Transactional
   void initTable() {
      List<Test2Entity> entities = new ArrayList<>();

      for (Map.Entry<String, Integer> entry : MAP.entrySet()) {
         Test2Entity entity = Test2Entity.builder()
            .stationId(entry.getKey())
            .value(entry.getValue())
            .build();

         entities.add(entity);
      }

      repository.saveAll(entities);
   }

   private static final Map<String, Integer> MAP = new HashMap<String, Integer>() {
      {
         put("57032", 8);
         put("57132", 8);
         put("57039", 9);
         put("57044", 6);
         put("57047", 8);
         put("57040", 4);
         put("57131", 3);
      }
   };

}
