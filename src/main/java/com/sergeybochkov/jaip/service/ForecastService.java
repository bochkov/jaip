package com.sergeybochkov.jaip.service;

import com.sergeybochkov.jaip.model.forecast.ForecastDaily;
import com.sergeybochkov.jaip.model.forecast.ForecastHourly;

public interface ForecastService {

    String FORECAST_URL_DAILY = "https://api.openweathermap.org/data/2.5/forecast/daily";
    String FORECAST_URL = "https://api.openweathermap.org/data/2.5/forecast";

    ForecastHourly hourly(String city);

    ForecastHourly hourly(String city, String lang);

    ForecastDaily daily(String city);

    ForecastDaily daily(String city, String lang);
}
