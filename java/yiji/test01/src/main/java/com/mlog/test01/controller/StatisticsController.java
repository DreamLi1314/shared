package com.mlog.test01.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class StatisticsController {

   private final JdbcTemplate jdbcTemplate;

   public StatisticsController(JdbcTemplate jdbcTemplate) {
      this.jdbcTemplate = jdbcTemplate;
   }

   @GetMapping("/statistics/pre_sum")
   public Object preSum() {
      String sql = "select station_id_c, round(sum(pre_time_2020)::numeric, 1) elem_data\n" +
         "from tb_xian_mul_day\n" +
         "where datetime >= '2011-01-01' and datetime < '2021-01-01' and pre_time_2020 > 50 and pre_time_2020 < 1000 and city in ('西安', '西安市') and station_id_c in (\n" +
         "\t\tselect station_code \n" +
         "\t\tfrom t_region_station \n" +
         "\t\twhere start_using_time >= '2011-01-01'\n" +
         "\t\tunion all \n" +
         "\t\tselect station_code \n" +
         "\t\tfrom t_national_station\n" +
         "\t)\n" +
         "group by station_id_c";

      return buildResult(sql);
   }

   @GetMapping("/statistics/pre_count")
   public Object preCount() {
      String sql = "select station_id_c, count(pre_time_2020) elem_data\n" +
         "from tb_xian_mul_day\n" +
         "where datetime >= '2011-01-01' and datetime < '2021-01-01' and pre_time_2020 > 50 and pre_time_2020 < 900 and city in ('西安', '西安市') and station_id_c in (\n" +
         "\t\tselect station_code \n" +
         "\t\tfrom t_region_station \n" +
         "\t\twhere start_using_time >= '2011-01-01'\n" +
         "\t\tunion all \n" +
         "\t\tselect station_code \n" +
         "\t\tfrom t_national_station\n" +
         "\t)\n" +
         "group by  station_id_c";

      return buildResult(sql);
   }

   @GetMapping("/statistics/tem_max_count")
   public Object temMaxCount() {
      String sql = "select station_id_c, count(tem_max) elem_data\n" +
         "from tb_xian_mul_day\n" +
         "where datetime >= '2011-01-01' and datetime < '2021-01-01' and tem_max > 35 and tem_max < 900 and city in ('西安', '西安市') and station_id_c in (\n" +
         "\t\tselect station_code \n" +
         "\t\tfrom t_region_station \n" +
         "\t\twhere start_using_time >= '2011-01-01'\n" +
         "\t\tunion all \n" +
         "\t\tselect station_code \n" +
         "\t\tfrom t_national_station\n" +
         "\t)\n" +
         "group by  station_id_c";

      return buildResult(sql);
   }

   @GetMapping("/statistics/tem_min_count")
   public Object temMinCount() {
      String sql = "select station_id_c, count(tem_min) elem_data\n" +
         "from tb_xian_mul_day\n" +
         "where datetime >= '2011-01-01' and datetime < '2021-01-01' and tem_min < 0.0 and tem_min > -30 and city in ('西安', '西安市') and station_id_c in (\n" +
         "\t\tselect station_code \n" +
         "\t\tfrom t_region_station \n" +
         "\t\twhere start_using_time >= '2011-01-01'\n" +
         "\t\tunion all \n" +
         "\t\tselect station_code \n" +
         "\t\tfrom t_national_station\n" +
         "\t)\n" +
         "group by  station_id_c";

      return buildResult(sql);
   }

   @GetMapping("/statistics/wind_count")
   public Object windCount() {
      String sql = "select station_id_c, count(win_s_max) elem_data\n" +
         "from tb_xian_mul_day\n" +
         "where  datetime >= '2011-01-01' and datetime < '2021-01-01' and win_s_max >= 17.2 and win_s_max < 1000 and city in ('西安', '西安市') and station_id_c in (\n" +
         "\t\tselect station_code \n" +
         "\t\tfrom t_region_station \n" +
         "\t\twhere start_using_time >= '2011-01-01'\n" +
         "\t\tunion all \n" +
         "\t\tselect station_code \n" +
         "\t\tfrom t_national_station\n" +
         "\t)\n" +
         "group by  station_id_c";

      return buildResult(sql);
   }

   @GetMapping("/statistics/snow_count")
   public Object snowCount() {
      String sql = "select station_id_c, count(snow) elem_data\n" +
         "from tb_xian_mul_day\n" +
         "where datetime >= '2011-01-01' and datetime < '2021-01-01' and snow = '1' and city in ('西安', '西安市') and station_id_c in (\n" +
         "\t\tselect station_code \n" +
         "\t\tfrom t_region_station \n" +
         "\t\twhere start_using_time >= '2011-01-01'\n" +
         "\t\tunion all \n" +
         "\t\tselect station_code \n" +
         "\t\tfrom t_national_station\n" +
         "\t)\n" +
         "group by  station_id_c";

      return buildResult(sql);
   }

   private Map<String, Object> queryLatLon(String stationId) {
      String sql = "select *\n" +
         "from (\n" +
         "\tselect station_code , lat, lon from t_national_station\n" +
         "\tunion all\n" +
         "\tselect station_code , lat, lon from t_region_station\n" +
         ") t\n" +
         "where station_code = '" + stationId + "'";

      return jdbcTemplate.queryForMap(sql);
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

}
