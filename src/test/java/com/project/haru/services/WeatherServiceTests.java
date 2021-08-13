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
package com.project.haru.services;

import com.project.haru.exceptions.UnknownCountryException;
import com.project.haru.persistence.entities.WeatherRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Sql("classpath:sql/test-data.sql")
@TestPropertySource("classpath:application-test.properties")
public class WeatherServiceTests {

    @Autowired
    private WeatherService weatherService;

    @Test
    public void getTodaysWeather_GetsTodaysWeather() throws UnknownCountryException {
        // Given: Entries in the database for the weather in spain
        // (loaded in the SQL script)

        // When: the user requests today's weather in spain
        WeatherRecord weather = weatherService.getTodaysWeather("spain");

        // Then: the service returns today's weather in spain
        assertThat(weather.getDescription()).isEqualTo("too hot");
    }

    @Test
    public void getTodaysWeather_withAnUnknownCountry_throwsUnknownCountryException() throws UnknownCountryException {
        // Given: Entries in the database
        // (loaded in the SQL)

        // When: the user requests today's weather for a country that is not in the database
        assertThatThrownBy(() -> weatherService.getTodaysWeather("sokovia"))
        // Then: the service throws an UnknownCountryException
        .isInstanceOfSatisfying(UnknownCountryException.class, exception -> {
            assertThat(exception.getCountry()).isEqualTo("sokovia");
        });
    }

}
