/*
 *
 * Copyright 2021 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.project.haru.controllers;

import com.project.haru.dto.WeatherDTO;
import com.project.haru.persistence.entities.WeatherRecord;
import com.project.haru.services.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SimpleController.class)
public class SimpleControllerTests {

    public static final WeatherRecord WEATHER_INFO = new WeatherRecord("spain", 29, 35, "Too hot");
    @Autowired
    private MockMvc mvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    public void getWeather_returnsJson_withWeatherInfo() throws Exception {
        // Given: the weather service returns information about the weather
        when(weatherService.getTodaysWeather("spain")).thenReturn(WEATHER_INFO);

        // When: the user requests the weather info from the weather.json endpoint
        ResultActions result = mvc.perform(get("/spain/weather.json"));

        // Then: it returns HTTP 200 with a JSON payload containing the weather info
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{" +
                        "\"minTemperature\":"+WEATHER_INFO.getMinTemperature()+", " +
                        "\"maxTemperature\":"+WEATHER_INFO.getMaxTemperature()+", " +
                        "\"description\": \""+WEATHER_INFO.getDescription()+"\"" +
                        "}"));
    }

    @Test
    public void getWeatherPage_returnsHtmlPage_withTheWeather() throws Exception {
        // Given: the weather service returns information about the weather
        when(weatherService.getTodaysWeather("spain")).thenReturn(WEATHER_INFO);

        // When: the user requests the weather info from the weather.json endpoint
        ResultActions result = mvc.perform(get("/spain/weather"));

        // Then: it returns HTTP 200 with a JSON payload containing the weather info
        result.andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(containsString("The weather in spain is Too hot")));
    }
}
