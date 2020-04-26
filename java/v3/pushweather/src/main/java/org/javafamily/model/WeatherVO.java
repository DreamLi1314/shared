package org.javafamily.model;

import java.io.Serializable;

public class WeatherVO implements Serializable {
    private RealTime realTime;
    private Life life;
    private Weather weather;
    private Pm25 pm25;
    private int isForeign;

    public RealTime getRealTime() {
        return realTime;
    }

    public void setRealTime(RealTime realTime) {
        this.realTime = realTime;
    }

    public Life getLife() {
        return life;
    }

    public void setLife(Life life) {
        this.life = life;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Pm25 getPm25() {
        return pm25;
    }

    public void setPm25(Pm25 pm25) {
        this.pm25 = pm25;
    }

    public int getIsForeign() {
        return isForeign;
    }

    public void setIsForeign(int isForeign) {
        this.isForeign = isForeign;
    }
}
