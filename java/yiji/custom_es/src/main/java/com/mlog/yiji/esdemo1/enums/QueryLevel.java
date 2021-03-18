package com.mlog.yiji.esdemo1.enums;

import com.mlog.yiji.esdemo1.util.QueryLevelMappingUtil;

import static com.mlog.yiji.esdemo1.util.QueryLevelMappingUtil.LEVEL_CITY;
import static com.mlog.yiji.esdemo1.util.QueryLevelMappingUtil.LEVEL_DISTRICT;

public enum QueryLevel {
   DISTRICT(1, LEVEL_DISTRICT), // 显示市区
   CITY(2, LEVEL_CITY), // 显示城市
   PROVINCE_CAPITAL(8, QueryLevelMappingUtil.PROVINCE_CAPITAL), // 显示省会城市
   CAPITAL(16 | PROVINCE_CAPITAL.getLevel(), QueryLevelMappingUtil.CAPITAL), // 显示首都
   P_CAPITAL_CITY(32, QueryLevelMappingUtil.P_CAPITAL_CITY) // 显示省会和城市
   ;


   private int level;
   private String key;
   private QueryLevel[] items;

   QueryLevel(int level, String key) {
      this.level = level;
      this.key = key;
   }
   QueryLevel(int level, String key, QueryLevel... items) {
      this.level = level;
      this.key = key;
   }


   public int getLevel() {
      return level;
   }

   public String getKey() {
      return key;
   }
}
