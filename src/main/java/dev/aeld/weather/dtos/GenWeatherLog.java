package dev.aeld.weather.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenWeatherLog {
    private String user;
    private String endpoint;
    private String city;
    private String response;
}
