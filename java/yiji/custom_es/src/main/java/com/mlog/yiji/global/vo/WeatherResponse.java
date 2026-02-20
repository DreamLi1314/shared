package com.mlog.yiji.global.vo;

import java.io.Serializable;

public class WeatherResponse implements Serializable {
   private String status;
   private WeatherResponseResult result;

   public String getStatus() {
      return status;
   }

   public String getCode() {
      if(result == null || result.realtime == null) {
         return null;
      }

      return result.realtime.getCode();
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public WeatherResponseResult getResult() {
      return result;
   }

   public void setResult(WeatherResponseResult result) {
      this.result = result;
   }

   public static class WeatherResponseResult {
      private Object location;
      private WeatherRealtime realtime;

      public Object getLocation() {
         return location;
      }

      public void setLocation(Object location) {
         this.location = location;
      }

      public WeatherRealtime getRealtime() {
         return realtime;
      }

      public void setRealtime(WeatherRealtime realtime) {
         this.realtime = realtime;
      }
   }
}
