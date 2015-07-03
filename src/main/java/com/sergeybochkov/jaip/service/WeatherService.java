package com.sergeybochkov.jaip.service;

import com.sergeybochkov.jaip.model.forecast.Weather;

public interface WeatherService {

    public static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";

    public Weather get();

    public Weather get(String city);
}
