package com.mathew.weather.controller;

import com.mathew.weather.model.Weather;
import com.mathew.weather.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WeatherControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WeatherService weatherService;

    @Test
    public void testGetWeatherWithDefaultCity() throws Exception {
        Weather mockWeather = new Weather();
        mockWeather.setTemperature(25);
        mockWeather.setWindSpeed(2);
        when(weatherService.getWeather("Melbourne")).thenReturn(mockWeather);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/weather"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.wind_speed").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.temperature_degrees").value(25));
    }

    @Test
    public void testGetWeatherWithSpecifiedCity() throws Exception {
        Weather mockWeather = new Weather();
        mockWeather.setTemperature(25);
        mockWeather.setWindSpeed(2);
        when(weatherService.getWeather("Sydney")).thenReturn(mockWeather);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/weather?city=Sydney"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.wind_speed").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.temperature_degrees").value(25));
    }
}