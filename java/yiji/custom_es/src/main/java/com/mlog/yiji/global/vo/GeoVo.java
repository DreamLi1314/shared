package com.mlog.yiji.global.vo;

import java.io.Serializable;

public class GeoVo implements Serializable {
   private String areaCode;
   private String areaName;
   private String realTimeCode;
   private double lon;
   private double lat;

   public String getAreaCode() {
      return areaCode;
   }

   public void setAreaCode(String areaCode) {
      this.areaCode = areaCode;
   }

   public String getAreaName() {
      return areaName;
   }

   public void setAreaName(String areaName) {
      this.areaName = areaName;
   }

   public String getRealTimeCode() {
      return realTimeCode;
   }

   public void setRealTimeCode(String realTimeCode) {
      this.realTimeCode = realTimeCode;
   }

   public double getLon() {
      return lon;
   }

   public void setLon(double lon) {
      this.lon = lon;
   }

   public double getLat() {
      return lat;
   }

   public void setLat(double lat) {
      this.lat = lat;
   }

   @Override
   public String toString() {
      return "GeoVo{" +
         "areaCode='" + areaCode + '\'' +
         ", areaName='" + areaName + '\'' +
         ", realTimeCode='" + realTimeCode + '\'' +
         ", lon=" + lon +
         ", lat=" + lat +
         '}';
   }
}
