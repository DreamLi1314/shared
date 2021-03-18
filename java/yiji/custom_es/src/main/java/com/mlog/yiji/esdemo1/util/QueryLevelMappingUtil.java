package com.mlog.yiji.esdemo1.util;

import com.mlog.yiji.esdemo1.enums.QueryLevel;
import org.elasticsearch.index.query.*;

public class QueryLevelMappingUtil {

   public static final String CAPITAL = "level_capital";
   public static final String PROVINCE_CAPITAL = "level_province_capital";
   public static final String P_CAPITAL_CITY = "level_p_capital_city";
   public static final String LEVEL_CITY = "level_city";
   public static final String LEVEL_DISTRICT = "level_district";

   public static QueryLevel mappingQueryLevel(int zoom){
      if(zoom < 5) {
         return QueryLevel.CAPITAL;
      }
      else if(zoom < 7) {
         return QueryLevel.PROVINCE_CAPITAL;
      }
      else if(zoom < 9) {
         return QueryLevel.P_CAPITAL_CITY;
      }
      else if(zoom < 11) {
         return QueryLevel.CITY;
      }
      else {
         return QueryLevel.DISTRICT;
      }
   }

   public static QueryBuilder getPostFilter(QueryLevel queryLevel) {
      if(queryLevel == null) {
         return null;
      }

//      MultiMatchQueryBuilder multiMatchQueryBuilder
//         = new MultiMatchQueryBuilder(
//            true, "level_province_capital", "level_city");
//
//      return multiMatchQueryBuilder;

      MatchQueryBuilder builder
         = new MatchQueryBuilder(queryLevel.getKey(),
         true);

      return builder;
   }
}
