package com.mathew.weather.client;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.mathew.weather.model.WeatherClientTwoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@ExtendWith(MockitoExtension.class)
public class WeatherClientTwoUnitTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherClientTwo weatherClientTwo;

    @BeforeEach
    public void setup() {
        // Inject mock values for @Value fields
        // These are private, so we use reflection or a subclass
        weatherClientTwo = new WeatherClientTwo(restTemplate);
        setField(weatherClientTwo, "url", "https://api.openweathermap.org/data/2.5/");
        setField(weatherClientTwo, "key", "fake-api-key");
    }

    @Test
    public void testGetWeather_ReturnsResponse() {
        String city = "London";
        URI uri = UriComponentsBuilder
                .fromUriString("https://api.openweathermap.org/data/2.5/weather")
                .queryParam("q", city)
                .queryParam("appid", "fake-api-key")
                .build().toUri();

        WeatherClientTwoResponse mockResponse = new WeatherClientTwoResponse();
        ResponseEntity<WeatherClientTwoResponse> responseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);

        when(restTemplate.getForEntity(uri, WeatherClientTwoResponse.class)).thenReturn(responseEntity);

        WeatherClientTwoResponse result = weatherClientTwo.getWeather(city);

        assertNotNull(result);
        verify(restTemplate, times(1)).getForEntity(uri, WeatherClientTwoResponse.class);
    }

    // Utility method to set private fields via reflection
    private void setField(Object target, String fieldName, Object value) {
        try {
            java.lang.reflect.Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}