package org.javafamily.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;

public class Pm25 implements Serializable {
    private String key; // 键
    private String show_desc; // 描述
    private String dateTime; // 时间日期
    private String cityName; // 城市
    // TODO Pm25 ---> Pm25Info
    @JsonDeserialize(as = Pm25Info.class)
    private Pm25Info pm25; // pm25

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getShow_desc() {
        return show_desc;
    }

    public void setShow_desc(String show_desc) {
        this.show_desc = show_desc;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Pm25Info getPm25() {
        return pm25;
    }

    public void setPm25(Pm25Info pm25) {
        this.pm25 = pm25;
    }

    class Pm25Info implements Serializable {
        private String curPm; // 当前 PM 值
        private String des; // PM 描述
        private String level; // 等级
        private String pm10; // pm10 值
        private String pm25; // pm25 值
        private String quality; // 质量

        public String getCurPm() {
            return curPm;
        }

        public void setCurPm(String curPm) {
            this.curPm = curPm;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getPm10() {
            return pm10;
        }

        public void setPm10(String pm10) {
            this.pm10 = pm10;
        }

        public String getPm25() {
            return pm25;
        }

        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }
    }
}
