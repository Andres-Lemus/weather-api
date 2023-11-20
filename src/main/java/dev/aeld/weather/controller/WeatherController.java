package dev.aeld.weather.controller;

import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.benmanes.caffeine.cache.Cache;

import dev.aeld.weather.entities.WeatherLog;
import dev.aeld.weather.services.UserService;
import dev.aeld.weather.services.WeatherLogService;
import dev.aeld.weather.services.WeatherService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;


@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private WeatherLogService weatherLogService;

    @Autowired
    private UserService userService;

    @Autowired
    CacheManager cacheManager;

    private final Bucket bucket;
    
    public WeatherController() {
        Bandwidth limit = Bandwidth.classic(100, Refill.intervally(100, Duration.ofHours(1)));
        this.bucket = Bucket.builder()
            .addLimit(limit)
            .build();
    }
    
    @GetMapping("/current")
    @Operation(summary = "Weather details from the current day of the specified city")
    public ResponseEntity<String> currentWeather(@Parameter(description = "Name of the city to search data")
    @RequestParam String city){
        if (bucket.tryConsume(1)) {
            String current = "";
            WeatherLog log = new WeatherLog();
            log.setUser(userService.authenticatedUser().getEmail());
            log.setCity(city);
            log.setEndpoint("/current");

            try {
            current = weatherService.getWeatherData(city).toString();
            log.setResponse(HttpStatus.ACCEPTED.toString());
            weatherLogService.addLog(log);
            return ResponseEntity.ok(current);
            } catch (Exception e) {
                log.setResponse(HttpStatus.NOT_FOUND.toString());
                weatherLogService.addLog(log);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @GetMapping("/pollution")
    @Operation(summary = "Pollution details from the specified city")
    public ResponseEntity<String> cityPollution(@Parameter(description = "Name of the city to search data")
    @RequestParam String city){
        if (bucket.tryConsume(1)) {
            String pollution = "";
            WeatherLog log = new WeatherLog();
            log.setUser(userService.authenticatedUser().getEmail());
            log.setCity(city);
            log.setEndpoint("/pollution");

            try {
                pollution = weatherService.getPollutionData(city).toString();
                log.setResponse(HttpStatus.ACCEPTED.toString());
                weatherLogService.addLog(log);
                return ResponseEntity.ok(pollution);
            } catch (Exception e) {
                log.setResponse(HttpStatus.NOT_FOUND.toString());
                weatherLogService.addLog(log);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @GetMapping("/forecast")
    @Operation(summary = "Weather details for the next 5 days in the specified city")
    public ResponseEntity<String> cityForecast(@Parameter(description = "Name of the city to search data") 
    @RequestParam String city){
        if (bucket.tryConsume(1)) {
            String forecast = "";
            WeatherLog log = new WeatherLog();
            log.setUser(userService.authenticatedUser().getEmail());
            log.setCity(city);
            log.setEndpoint("/forecast");
       
            try {
                forecast = weatherService.getForecastData(city).toString();
                log.setResponse(HttpStatus.ACCEPTED.toString());
                weatherLogService.addLog(log);
                return ResponseEntity.ok(forecast);
            } catch (Exception e) {
                log.setResponse(HttpStatus.NOT_FOUND.toString());
                weatherLogService.addLog(log);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @GetMapping(value = "/inspectCache")
    public void inspectCache(String cacheName) {

        CaffeineCache caffeineCache = (CaffeineCache) cacheManager.getCache("WeatherCache");
        Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();

        for (Map.Entry<Object, Object> entry : nativeCache.asMap().entrySet()) {

            System.out.println("Key = " + entry.getKey());
            System.out.println("Value = " + entry.getValue());
        }
    }

}
