package com.mathew.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Weather {

    @JsonProperty("wind_speed")
    private Integer windSpeed;

    @JsonProperty("temperature_degrees")
    private Integer temperature;

    public Integer getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Integer windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }
}
