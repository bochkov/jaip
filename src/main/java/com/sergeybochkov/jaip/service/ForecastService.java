package com.sergeybochkov.jaip.service;

import com.sergeybochkov.jaip.model.forecast.ForecastDaily;
import com.sergeybochkov.jaip.model.forecast.ForecastHorly;

public interface ForecastService {

    public static final String FORECAST_URL_DAILY = "http://api.openweathermap.org/data/2.5/forecast/daily";
    public static final String FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast";

    public ForecastHorly get();

    public ForecastHorly get(String city);

    public ForecastDaily getDaily();

    public ForecastDaily getDaily(String city);
}
