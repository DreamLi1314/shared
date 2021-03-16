package com.mlog.yiji.esdemo1.util;

import com.mlog.yiji.esdemo1.enums.QueryLevel;
import org.elasticsearch.index.query.*;

public class QueryLevelMappingUtil {

   public static final String CAPITAL = "level_capital";
   public static final String PROVINCE_CAPITAL = "level_province_capital";
   public static final String LEVEL_CITY = "level_city";
   public static final String LEVEL_DISTRICT = "level_district";

   public static QueryLevel mappingQueryLevel(int zoom){
      if(zoom < 5) {
         return QueryLevel.CAPITAL;
      }
      else if(zoom < 8) {
         return QueryLevel.PROVINCE_CAPITAL;
      }
      else if(zoom < 10) {
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

      MatchQueryBuilder builder
         = new MatchQueryBuilder(queryLevel.getKey(),
         true);

      return builder;
   }
}
