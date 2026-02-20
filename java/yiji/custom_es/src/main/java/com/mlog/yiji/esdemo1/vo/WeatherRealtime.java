package com.mlog.yiji.esdemo1.vo;

import java.io.Serializable;

public class WeatherRealtime implements Serializable {
   private String text;
   private String code;

   public String getText() {
      return text;
   }

   public void setText(String text) {
      this.text = text;
   }

   public String getCode() {
      return code;
   }

   public void setCode(String code) {
      this.code = code;
   }
}
