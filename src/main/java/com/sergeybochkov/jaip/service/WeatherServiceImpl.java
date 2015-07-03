package com.sergeybochkov.jaip.service;

import com.sergeybochkov.jaip.helper.Helper;
import com.sergeybochkov.jaip.helper.Settings;
import com.sergeybochkov.jaip.model.forecast.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private Helper helper;

    @Override
    public Weather get() {
        return get(Settings.DEFAULT_CITY);
    }

    @Override
    public Weather get(String city) {
        String units = "metric";
        String params = "q=" + city + "&units=" + units + "&lang=" + Settings.DEFAULT_LANG;
        return helper.doGetJson(WEATHER_URL, params, Weather.class);
    }
}
