package com.mathew.weather.client;

import com.mathew.weather.model.WeatherClientOneResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class WeatherClientOne {


    @Value("${weatherstack.url}")
    private String url;

    @Value("${weatherstack.key}")
    private String key;

    private final RestTemplate restTemplate;

    @Autowired
    public WeatherClientOne(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherClientOneResponse getWeather(final String city) {
        URI uri = UriComponentsBuilder
                .fromUriString(url + "/current")
                .queryParam("access_key", key)
                .queryParam("query", city)
                .build().toUri();
        ResponseEntity<WeatherClientOneResponse> response = restTemplate.getForEntity(uri, WeatherClientOneResponse.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new HttpClientErrorException(response.getStatusCode());
        }
        if (ObjectUtils.isEmpty(response.getBody().getCurrent())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Error in request");
        }
        return response.getBody();
    }
}
