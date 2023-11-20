package dev.aeld.weather;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import dev.aeld.weather.controller.WeatherController;


@WebMvcTest(WeatherController.class)
public class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser("spring")
    @Test
    public void it_should_return_current_city_weather_data() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/weather/current").param("city", "San Salvador")
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithMockUser("spring")
    @Test
    public void it_should_return_pollution_city_data() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/weather/pollution").param("city", "San Salvador")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.list").isArray());
    }

    @WithMockUser("spring")
    @Test
    public void it_should_return_forecast_city_data() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/weather/forecast").param("city", "San Salvador")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[3]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[4]").exists());
    }
    
}
