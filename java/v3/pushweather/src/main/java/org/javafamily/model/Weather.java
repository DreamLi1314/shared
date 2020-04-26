package org.javafamily.model;

import java.io.Serializable;

public class Weather implements Serializable {
    private WeatherItem[] weather;

    public WeatherItem[] getWeather() {
        return weather;
    }

    public void setWeather(WeatherItem[] weather) {
        this.weather = weather;
    }
}
