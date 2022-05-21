package com.sergeybochkov.jaip.service;

import com.sergeybochkov.jaip.helper.Resource;
import com.sergeybochkov.jaip.model.forecast.Weather;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class WeatherServiceImpl implements WeatherService {

    private final Resource resource;

    @Value("${openweather.api.key}")
    private String apiKey;

    @Override
    public Weather get(String city) {
        return get(city, "ru");
    }

    @Override
    public Weather get(String city, String lang) {
        String units = "metric";
        String params = "q=" + city +
                "&units=" + units +
                "&lang=" + lang +
                "&APPID=" + apiKey;
        return resource.getJson(WEATHER_URL, params, Weather.class);
    }
}
