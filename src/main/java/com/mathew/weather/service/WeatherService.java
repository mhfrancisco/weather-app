package com.mathew.weather.service;

import com.mathew.weather.client.WeatherClientOne;
import com.mathew.weather.client.WeatherClientTwo;
import com.mathew.weather.model.Weather;
import com.mathew.weather.model.Current;
import com.mathew.weather.model.WeatherClientOneResponse;
import com.mathew.weather.model.WeatherClientTwoResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    private static final double KELVIN_CONSTANT = 273.15;

    private final WeatherClientOne weatherClientOne;
    private final WeatherClientTwo weatherClientTwo;

    @Autowired
    public WeatherService(final WeatherClientOne weatherClientOne,
                          final WeatherClientTwo weatherClientTwo) {
        this.weatherClientOne = weatherClientOne;
        this.weatherClientTwo = weatherClientTwo;
    }

    @Cacheable("weather")
    @CircuitBreaker(name = "weatherService", fallbackMethod = "fallbackWeather")
    public Weather getWeather(final String city) {
        WeatherClientTwoResponse response = weatherClientTwo.getWeather(city);
        Integer windSpeed = response.getWind().getSpeed().intValue();
        double temp = response.getMain().getTemp().doubleValue() - KELVIN_CONSTANT;
        return createWeather(windSpeed, (int) temp);
    }

    private Weather fallbackWeather(final String city, final Throwable e) {
        logger.error(e.getMessage(), e);
        WeatherClientOneResponse response = weatherClientOne.getWeather(city);
        Current current = response.getCurrent();
        return createWeather(current.getWindSpeed(), current.getTemperature());
    }

    private Weather createWeather(final Integer windSpeed, final Integer temperature) {
        Weather weather = new Weather();
        weather.setTemperature(temperature);
        weather.setWindSpeed(windSpeed);
        return weather;
    }

    @CacheEvict(value = "weather", allEntries = true)
    @Scheduled(fixedRateString = "${caching.spring.weatherTTL}")
    public void emptyWeatherCache() {
        logger.info("emptying weather cache");
    }
}
