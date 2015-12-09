package com.sergeybochkov.jaip.service;

import com.sergeybochkov.jaip.helper.Helper;
import com.sergeybochkov.jaip.helper.Settings;
import com.sergeybochkov.jaip.model.forecast.ForecastDaily;
import com.sergeybochkov.jaip.model.forecast.ForecastHorly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ForecastServiceImpl implements ForecastService {

    @Autowired
    private Helper helper;

    @Value("${openweather.api.key}")
    private String API_KEY;

    @Override
    public ForecastHorly hourly() {
        return hourly(Settings.DEFAULT_CITY);
    }

    @Override
    public ForecastHorly hourly(String city) {
        String units = "metric";
        Integer cnt = 15;
        String params = "q=" + city +
                "&units=" + units +
                "&lang=" + Settings.DEFAULT_LANG +
                "&cnt=" + cnt +
                "&APPID=" + API_KEY;
        return helper.doGetJson(FORECAST_URL, params, ForecastHorly.class);
    }

    @Override
    public ForecastDaily daily() {
        return daily(Settings.DEFAULT_CITY);
    }

    @Override
    public ForecastDaily daily(String city) {
        String units = "metric";
        Integer cnt = 15;
        String params = "q=" + city +
                "&units=" + units +
                "&lang=" + Settings.DEFAULT_LANG +
                "&cnt=" + cnt +
                "&APPID=" + API_KEY;
        return helper.doGetJson(FORECAST_URL_DAILY, params, ForecastDaily.class);
    }


}
