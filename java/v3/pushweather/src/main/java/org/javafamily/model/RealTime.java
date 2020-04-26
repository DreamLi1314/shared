package org.javafamily.model;

import java.io.Serializable;

public class RealTime implements Serializable {
   private String date; // 测量日期
   private String time; // 测量时间
   private String week; // 星期
   private String city; // 城市
   private String lunar; // 农历
   private RealTimeWeather realTimeWeather; // 实时天气情况

   public String getDate() {
      return date;
   }

   public void setDate(String date) {
      this.date = date;
   }

   public String getTime() {
      return time;
   }

   public void setTime(String time) {
      this.time = time;
   }

   public String getWeek() {
      return week;
   }

   public void setWeek(String week) {
      this.week = week;
   }

   public String getCity() {
      return city;
   }

   public void setCity(String city) {
      this.city = city;
   }

   public String getLunar() {
      return lunar;
   }

   public void setLunar(String lunar) {
      this.lunar = lunar;
   }

   public RealTimeWeather getRealTimeWeather() {
      return realTimeWeather;
   }

   public void setRealTimeWeather(RealTimeWeather realTimeWeather) {
      this.realTimeWeather = realTimeWeather;
   }

   class RealTimeWeather {
      private String humidity; // 湿度
      private String temperature; // 温度
      private String info; // 天气情况

      public String getHumidity() {
         return humidity;
      }

      public void setHumidity(String humidity) {
         this.humidity = humidity;
      }

      public String getTemperature() {
         return temperature;
      }

      public void setTemperature(String temperature) {
         this.temperature = temperature;
      }

      public String getInfo() {
         return info;
      }

      public void setInfo(String info) {
         this.info = info;
      }
   }
}
