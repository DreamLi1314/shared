package org.javafamily.model;

import java.io.Serializable;

public class WeatherItem implements Serializable {
    private String date;
    private String week;
    private String nongli;
    private Info info;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getNongli() {
        return nongli;
    }

    public void setNongli(String nongli) {
        this.nongli = nongli;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    class Info implements Serializable {
        private String[] dawn;
        private String[] day;
        private String[] night;

        public String[] getDawn() {
            return dawn;
        }

        public void setDawn(String[] dawn) {
            this.dawn = dawn;
        }

        public String[] getDay() {
            return day;
        }

        public void setDay(String[] day) {
            this.day = day;
        }

        public String[] getNight() {
            return night;
        }

        public void setNight(String[] night) {
            this.night = night;
        }
    }
}
