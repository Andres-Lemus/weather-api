package dev.aeld.weather.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.aeld.weather.entities.WeatherLog;
import dev.aeld.weather.repositories.WeatherLogRepository;

@Service
public class WeatherLogService {
    @Autowired
    WeatherLogRepository weatherLogRepository;

    @Autowired
    UserService userService;

    public WeatherLog addLog(WeatherLog weatherLog) {
        return weatherLogRepository.save(weatherLog);
    }
}
