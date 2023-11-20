package dev.aeld.weather.services;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class WeatherService {

    public String formatTimestamp(long timestamp) {
        Date date = new Date(timestamp * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public JSONObject getCities(String city){
         String uri = UriComponentsBuilder.fromHttpUrl("http://api.openweathermap.org/geo/1.0/direct")
                        .queryParam("q", city)
                        .queryParam("limit", 5)
                        .queryParam("appid", "f81298c664b57436c2ae847296d88e0d")
                        .build()
                        .toUriString();
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        try {
            JSONArray citiesData = new JSONArray(result);
            JSONObject cityData = citiesData.getJSONObject(0);
            return cityData;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public JSONObject getWeatherData(String city) {
        try {
            JSONObject cityData = getCities(city);
            JSONObject weatherData = getCurrentWeather(cityData.getString("lat"), cityData.getString("lon"));
            return weatherData;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getCurrentWeather(String lat, String lon) {
        JSONObject weatherData = new JSONObject();
        String uri = UriComponentsBuilder.fromHttpUrl("https://api.openweathermap.org/data/2.5/weather")
                        .queryParam("lat", lat)
                        .queryParam("lon", lon)
                        .queryParam("appid", "f81298c664b57436c2ae847296d88e0d")
                        .build()
                        .toUriString();
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        try {
            weatherData = new JSONObject(result);
            return weatherData;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weatherData;
    }

    public JSONObject getPollutionData(String city) {
        try {
            JSONObject cityData = getCities(city);
            JSONObject pollutionData = getCityPollution(cityData.getString("lat"), cityData.getString("lon"));
            return pollutionData;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getCityPollution(String lat, String lon) {
        JSONObject pollutionData = new JSONObject();
        String uri = UriComponentsBuilder.fromHttpUrl("http://api.openweathermap.org/data/2.5/air_pollution")
                        .queryParam("lat", lat)
                        .queryParam("lon", lon)
                        .queryParam("appid", "f81298c664b57436c2ae847296d88e0d")
                        .build()
                        .toUriString();
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        try {
            pollutionData = new JSONObject(result);
            return pollutionData;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pollutionData;
    }

    public JSONArray getForecastData(String city) {
        try {
            JSONObject cityData = getCities(city);
            JSONArray forecastData = getCityForecast(cityData.getString("lat"), cityData.getString("lon"));
            return forecastData;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONArray getCityForecast(String lat, String lon) {
        JSONArray forecastFive = new JSONArray();
        String uri = UriComponentsBuilder.fromHttpUrl("http://api.openweathermap.org/data/2.5/forecast")
                        .queryParam("lat", lat)
                        .queryParam("lon", lon)
                        .queryParam("appid", "f81298c664b57436c2ae847296d88e0d")
                        .queryParam("exclude", "current,minutely,hourly,alerts")
                        .build()
                        .toUriString();
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        try {
            JSONObject forecastObj = new JSONObject(result);
            JSONArray forecastData = new JSONArray(forecastObj.getString("list"));
            forecastFive = new JSONArray();
            forecastFive.put(forecastData.getJSONObject(0));
            forecastFive.put(forecastData.getJSONObject(8));
            forecastFive.put(forecastData.getJSONObject(16));
            forecastFive.put(forecastData.getJSONObject(24));
            forecastFive.put(forecastData.getJSONObject(32));
            return forecastFive;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return forecastFive;
    }

}
