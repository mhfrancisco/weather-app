package com.mathew.weather.service;

import com.mathew.weather.client.WeatherClientOne;
import com.mathew.weather.client.WeatherClientTwo;
import com.mathew.weather.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceUnitTest {

    @Mock
    private WeatherClientOne weatherClientOne;

    @Mock
    private WeatherClientTwo weatherClientTwo;

    @InjectMocks
    private WeatherService weatherService;

    @Test
    void getWeather_shouldReturnWeather_whenClientTwoRespondsSuccessfully() {
        // Given
        String city = "London";
        Main main = new Main();
        main.setTemp((long) 300.15); // Kelvin

        Wind wind = new Wind();
        wind.setSpeed((long) 5.0);

        WeatherClientTwoResponse response = new WeatherClientTwoResponse();
        response.setMain(main);
        response.setWind(wind);

        when(weatherClientTwo.getWeather(city)).thenReturn(response);

        // When
        Weather weather = weatherService.getWeather(city);

        // Then
        assertEquals(26, weather.getTemperature());
        assertEquals(5, weather.getWindSpeed());
    }

    @Test
    void fallbackWeather_shouldReturnWeatherFromClientOne_whenClientTwoFails() {
        // Given
        String city = "Paris";
        Current current = new Current();
        current.setTemperature(20);
        current.setWindSpeed(7);

        WeatherClientOneResponse response = new WeatherClientOneResponse();
        response.setCurrent(current);

        when(weatherClientOne.getWeather(city)).thenReturn(response);

        // When
        Weather fallbackWeather = weatherService.fallbackWeather(city, new RuntimeException("Service down"));

        // Then
        assertEquals(20, fallbackWeather.getTemperature());
        assertEquals(7, fallbackWeather.getWindSpeed());
    }
}

