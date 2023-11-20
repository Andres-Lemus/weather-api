package dev.aeld.weather.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.aeld.weather.entities.WeatherLog;

public interface WeatherLogRepository extends JpaRepository<WeatherLog,Long>{
    
}
