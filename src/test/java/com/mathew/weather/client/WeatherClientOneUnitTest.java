package com.mathew.weather.client;

import com.mathew.weather.model.Current;
import com.mathew.weather.model.WeatherClientOneResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherClientOneUnitTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherClientOne weatherClientOne;

    @Test
    void testGetWeather_Success() {
        WeatherClientOneResponse mockResponse = new WeatherClientOneResponse();
        Current mockCurrent = new Current();
        mockResponse.setCurrent(mockCurrent);

        when(restTemplate.getForEntity(any(URI.class), Mockito.eq(WeatherClientOneResponse.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        WeatherClientOneResponse result = weatherClientOne.getWeather("Melbourne");

        assertNotNull(result);
        assertEquals(mockCurrent, result.getCurrent());
    }

    @Test
    void testGetWeather_Non2xxResponse() {
        when(restTemplate.getForEntity(any(URI.class), Mockito.eq(WeatherClientOneResponse.class)))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR));

        assertThrows(HttpClientErrorException.class, () -> weatherClientOne.getWeather("Melbourne"));
    }

    @Test
    void testGetWeather_EmptyBody() {
        WeatherClientOneResponse emptyResponse = new WeatherClientOneResponse();
        when(restTemplate.getForEntity(any(URI.class), Mockito.eq(WeatherClientOneResponse.class)))
                .thenReturn(new ResponseEntity<>(emptyResponse, HttpStatus.OK));

        assertThrows(HttpClientErrorException.class, () -> weatherClientOne.getWeather("Melbourne"));
    }
}
