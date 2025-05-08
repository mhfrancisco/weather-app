package com.mathew.weather.client;

import com.mathew.weather.model.WeatherClientTwoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class WeatherClientTwo {

    @Value("${openweathermap.url}")
    private String url;

    @Value("${openweathermap.key}")
    private String key;

    private final RestTemplate restTemplate;

    @Autowired
    public WeatherClientTwo(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherClientTwoResponse getWeather(final String city) {
        URI uri = UriComponentsBuilder
                .fromUriString(url + "weather")
                .queryParam("q", city)
                .queryParam("appid", key)
                .build().toUri();
        ResponseEntity<WeatherClientTwoResponse> response = restTemplate.getForEntity(uri, WeatherClientTwoResponse.class);
        return response.getBody();
    }
}
