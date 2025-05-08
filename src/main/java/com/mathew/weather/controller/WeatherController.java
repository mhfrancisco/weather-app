package com.mathew.weather.controller;

import com.mathew.weather.model.Weather;
import com.mathew.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(final WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public ResponseEntity<Weather> getWeather(
            @RequestParam(defaultValue = "Melbourne") final String city) {
        Weather weather = weatherService.getWeather(city);
        return ResponseEntity.ok().body(weather);
    }
}
