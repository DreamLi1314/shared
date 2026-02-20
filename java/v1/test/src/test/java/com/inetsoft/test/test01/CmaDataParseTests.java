package com.inetsoft.test.test01;

import com.inetsoft.test.dao.TestRepository;
import com.inetsoft.test.domain.TestEntity;
import com.inetsoft.test.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CmaDataParseTests {

   @Autowired
   private TestService service;

   @Test
   @Transactional
   public void parse() throws Exception {
      ClassPathResource resource = new ClassPathResource("111.txt");
      final InputStream in = resource.getInputStream();
      final InputStreamReader isr = new InputStreamReader(in, "gbk");
      final BufferedReader br = new BufferedReader(isr);

      final String header = br.readLine().trim();

      final String[] s = header.split("\\s+");
      System.out.println(s);

//      final String data = br.readLine().trim();
//      final String[] items = data.split("\\s+");
//      System.out.println(items);

      List<TestEntity> entityList = new ArrayList<>();

      br.lines().forEach(data -> {
         final String[] items = data.trim().split("\\s+");

         String stationId = items[0];
         int year = Integer.parseInt(items[1]);
         int month = Integer.parseInt(items[2]);
         int day = Integer.parseInt(items[3]);

         int lightning = Integer.parseInt(items[4]); // 日闪电
         int wind = Integer.parseInt(items[5]); // 日大风
         int snow = Integer.parseInt(items[6]); // 日积雪
         int thunder = Integer.parseInt(items[7]); // 日雷暴
         int dustStorm = Integer.parseInt(items[8]); // 日沙尘暴
         int hail = Integer.parseInt(items[9]); // 日冰雹
         int rain = Integer.parseInt(items[10]); // 日雨
         int daySnow = Integer.parseInt(items[11]); // 日雪

         TestEntity entity = TestEntity.builder()
            .stationId(stationId)
            .year(year)
            .month(month)
            .day(day)
            .lightning(lightning)
            .wind(wind)
            .snow(snow)
            .thunder(thunder)
            .dustStorm(dustStorm)
            .hail(hail)
            .rain(rain)
            .daySnow(daySnow)
            .build();

         entityList.add(entity);
      });

      service.saveAll(entityList);
      System.out.println(entityList.size());
   }

}
