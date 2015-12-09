package com.sergeybochkov.jaip.service;

import com.sergeybochkov.jaip.model.forecast.ForecastDaily;
import com.sergeybochkov.jaip.model.forecast.ForecastHorly;

public interface ForecastService {

    String FORECAST_URL_DAILY = "http://api.openweathermap.org/data/2.5/forecast/daily";
    String FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast";

    ForecastHorly hourly();

    ForecastHorly hourly(String city);

    ForecastDaily daily();

    ForecastDaily daily(String city);
}
