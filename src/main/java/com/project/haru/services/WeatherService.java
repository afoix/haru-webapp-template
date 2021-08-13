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
import com.project.haru.persistence.repositories.WeatherRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeatherService {

    @Autowired
    private WeatherRecordRepository weatherRepository;

    public WeatherRecord getTodaysWeather(String countryName) throws UnknownCountryException {
        return weatherRepository.findFirstByCountryOrderByTimestampDesc(countryName)
                .orElseThrow(() -> new UnknownCountryException(countryName));
    }
}
