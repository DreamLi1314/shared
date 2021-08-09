package com.inetsoft.test.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class TestController2 {
   private final JdbcTemplate jdbcTemplate;

   public TestController2(JdbcTemplate jdbcTemplate) {
      this.jdbcTemplate = jdbcTemplate;
   }

   @GetMapping("/statistics/thunder_sum")
   public Object thunderSum() {
      String sql = "select station_id station_id_c, sum(thunder) elem_data\n" +
         "from t_test\n" +
         "group by station_id";

      return buildResult(sql);
   }

   @GetMapping("/statistics/hail_sum")
   public Object hailSum() {
      String sql = "select station_id station_id_c, sum(hail) elem_data\n" +
         "from t_test\n" +
         "group by station_id";

      return buildResult(sql);
   }

   @GetMapping("/statistics/lit_sum")
   public Object litSum() {
      String sql = "select station_id station_id_c, sum(lightning) elem_data\n" +
         "from t_test\n" +
         "group by station_id";

      return buildResult(sql);
   }

   @GetMapping("/statistics/snow_sum")
   public Object snowSum() {
      String sql = "select station_id station_id_c, count(day_snow) elem_data\n" +
         "from t_test\n" +
         "where day_snow = '1'\n" +
         "group by station_id";

      return buildResult(sql);
   }

   @GetMapping("/statistics/test2")
   public Object test2() {
      String sql = "select station_id station_id_c, value elem_data\n" +
         "from t_test2";

      return buildResult(sql);
   }

   private Object buildResult(String sql) {
      final List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);

      List<Object> data = new ArrayList<>();
      List<Object> lon = new ArrayList<>();
      List<Object> lat = new ArrayList<>();

      for (Map<String, Object> map : maps) {
         final String stationId = (String) map.get("station_id_c");
         data.add(map.get("elem_data"));

         final Map<String, Object> latlon = queryLatLon(stationId);
         lon.add(latlon.get("lon"));
         lat.add(latlon.get("lat"));
      }

      System.out.println(data);

      Map<String, Object> result = new LinkedHashMap<>();

      result.put("lons", lon.toArray());
      result.put("lats", lat.toArray());
      result.put("data", data.toArray());
      result.put("original", maps);

      return result;
   }

   private Map<String, Object> queryLatLon(String stationId) {
      if("57036".equals(stationId)) {
         stationId = "V8870";
      }

      String sql = "select *\n" +
         "from (\n" +
         "\tselect station_code , lat, lon from t_national_station\n" +
         "\tunion all\n" +
         "\tselect station_code , lat, lon from t_region_station\n" +
         ") t\n" +
         "where station_code = '" + stationId + "'";

      return jdbcTemplate.queryForMap(sql);
   }
}
