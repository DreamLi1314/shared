package com.mlog.yiji.esdemo1.util;

import com.mlog.yiji.esdemo1.enums.QueryLevel;
import org.elasticsearch.index.query.*;

public class QueryLevelMappingUtil {

   public static final String CAPITAL = "level_capital";
   public static final String PROVINCE_CAPITAL = "level_province_capital";
   public static final String LEVEL_CITY = "level_city";
   public static final String LEVEL_DISTRICT = "level_district";

   public static QueryLevel mappingQueryLevel(Double zoom){
      if(zoom == null || zoom <= 4) {
         return QueryLevel.CAPITAL;
      }
      else if(zoom <= 5) {
         return QueryLevel.PROVINCE_CAPITAL;
      }
      else if(zoom <= 8) {
         return QueryLevel.CITY;
      }
      else {
         return QueryLevel.DISTRICT;
      }
   }

   public static QueryLevel mappingGlobalQueryLevel(Double zoom){
      if(zoom == null || zoom < 6) {
         return QueryLevel.CAPITAL;
      }
      else if(zoom <= 8) {
         return QueryLevel.PROVINCE_CAPITAL;
      }
      else {
         return QueryLevel.CITY;
      }
   }

   public static QueryBuilder getPostFilter(Double zoom, boolean global) {
      QueryLevel queryLevel = global
         ? mappingGlobalQueryLevel(zoom)
         : mappingQueryLevel(zoom);

      // if DISTRICT to find all
      if(queryLevel == QueryLevel.DISTRICT && !global
         // global no district
         || queryLevel == QueryLevel.CITY && global)
      {
         return null;
      }

      // capital always display
      MultiMatchQueryBuilder builder = new MultiMatchQueryBuilder(
         true, CAPITAL)
         .operator(Operator.OR)
         ;

      // if province capital
      if(queryLevel == QueryLevel.PROVINCE_CAPITAL) {
         builder.field(PROVINCE_CAPITAL);
      }

      if(queryLevel == QueryLevel.CITY) {
         builder.field(PROVINCE_CAPITAL);
         builder.field(LEVEL_CITY);
      }

      return builder;

//      MatchQueryBuilder builder
//         = new MatchQueryBuilder(queryLevel.getKey(),
//         true);

//      return builder;
   }
}
