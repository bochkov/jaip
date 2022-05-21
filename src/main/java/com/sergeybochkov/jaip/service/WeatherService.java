package com.sergeybochkov.jaip.service;

import com.sergeybochkov.jaip.model.forecast.Weather;

public interface WeatherService {

    String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";

    Weather get(String city);

    Weather get(String city, String lang);
}
