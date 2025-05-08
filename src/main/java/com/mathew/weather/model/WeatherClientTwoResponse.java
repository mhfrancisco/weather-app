package com.mathew.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherClientTwoResponse {

    @JsonProperty
    private Main main;

    @JsonProperty
    private Wind wind;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }
}
