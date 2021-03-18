package com.mlog.yiji.esdemo1;

import com.mlog.yiji.esdemo1.service.WeatherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WeatherServiceTests {

   @Autowired
   private WeatherService weatherService;

   @ParameterizedTest
   @ValueSource(strings = "101010100")
   public void testGetWeather(String areaCode) {
      String realtimeCode = weatherService.getRealtimeCode(
         areaCode, 116.405285, 39.904989);

      Assertions.assertNotNull(realtimeCode, "Query realTime code error.");
   }

}
