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
import com.project.haru.exceptions.UnknownCountryException;
import com.project.haru.persistence.entities.WeatherRecord;
import com.project.haru.services.WeatherService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.View;

@Controller
public class SimpleController {

    final WeatherService weatherService;

    public SimpleController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping(value = "/{country}/weather.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WeatherDTO> getWeather(@PathVariable String country) throws UnknownCountryException {
        final WeatherRecord todaysWeather = weatherService.getTodaysWeather(country);
        return ResponseEntity.ok(new WeatherDTO(todaysWeather.getMinTemperature(), todaysWeather.getMaxTemperature(), todaysWeather.getDescription()));
    }

    @GetMapping(value = "/{country}/weather", produces = MediaType.TEXT_HTML_VALUE)
    public String getWeatherPage(@PathVariable String country, Model model) throws UnknownCountryException {
        WeatherRecord theWeather = weatherService.getTodaysWeather(country);
        model.addAttribute("countryName", country);
        model.addAttribute("weather", theWeather);
        return "weather";
    }
}
