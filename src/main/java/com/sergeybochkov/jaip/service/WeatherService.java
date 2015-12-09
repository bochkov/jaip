package com.sergeybochkov.jaip.service;

import com.sergeybochkov.jaip.model.forecast.Weather;

public interface WeatherService {

    String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";

    Weather get();

    Weather get(String city);
}
