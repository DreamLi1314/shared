package com.mlog.yiji.esdemo1.service.impl;

import com.mlog.yiji.esdemo1.service.WeatherService;
import com.mlog.yiji.esdemo1.vo.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherServiceImpl implements WeatherService {

   private final RestTemplate restTemplate;

   @Autowired
   public WeatherServiceImpl(RestTemplate restTemplate) {
      this.restTemplate = restTemplate;
   }

   @Override
   public String getRealtimeCode(String areaCode) {
      ResponseEntity<WeatherResponse> response = restTemplate.getForEntity(
         "http://gfapi.mlogcn.com/weather/v001/now?areacode="
         + areaCode + "&key=TRFmZurYkr0zQZmj6GHe4zHADoyMBJhU",
         WeatherResponse.class);

      return response.getBody() != null ? response.getBody().getCode() : null;
   }
}
