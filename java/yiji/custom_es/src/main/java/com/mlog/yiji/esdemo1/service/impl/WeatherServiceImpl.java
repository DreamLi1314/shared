package com.mlog.yiji.esdemo1.service.impl;

import com.mlog.yiji.esdemo1.service.WeatherService;
import com.mlog.yiji.esdemo1.vo.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class WeatherServiceImpl implements WeatherService {

   private final RestTemplate restTemplate;

   @Autowired
   public WeatherServiceImpl(RestTemplate restTemplate) {
      this.restTemplate = restTemplate;
   }

   @Value("${mlog.weather.baseUri}")
   private String baseUri;

   @Value("${mlog.weather.realTime.api}")
   private String api;

   @Value("${mlog.weather.realTime.params}")
   private List<String> params;

   @Override
   public String getRealtimeCode(String areaCode, Double lon, Double lat) {
//     try {
//        return doQueryRealtimeCode(areaCode);
//     }
//     catch(Exception e) {
//        e.printStackTrace();
//     }

      Map<String, String> params = new HashMap<>();

      params.put("areacode", areaCode);
      params.put("lon", lon + "");
      params.put("lat", lat + "");
      params.put("lonlat", lon + "," + lat);
      params.put("key", "TRFmZurYkr0zQZmj6GHe4zHADoyMBJhU");

     return doQueryRealtimeCode(params);
   }

   private String doQueryRealtimeCode(Map<String, String> params) {
      StringBuilder url = new StringBuilder();

      url.append(this.baseUri);
      url.append(this.api);

      if(!CollectionUtils.isEmpty(params)) {
         url.append("?");

         boolean first = true;

         for(String key : this.params) {
            if(first) {
               first = false;
            }
            else {
               url.append("&");
            }

            url.append(key)
               .append("=")
               .append(params.get(key));
         }
      }

      ResponseEntity<WeatherResponse> response = restTemplate.getForEntity(url.toString(),
         WeatherResponse.class);

      return response.getBody() != null ? response.getBody().getCode() : null;
   }
}
