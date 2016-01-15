package com.sergeybochkov.jaip.service;

import com.sergeybochkov.jaip.helper.Helper;
import com.sergeybochkov.jaip.helper.Settings;
import com.sergeybochkov.jaip.model.forecast.ForecastDaily;
import com.sergeybochkov.jaip.model.forecast.ForecastHorly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForecastServiceImpl implements ForecastService {

    @Autowired
    private Helper helper;
    @Autowired
    private Settings settings;

    @Override
    public ForecastHorly hourly() {
        return hourly(settings.getDefaultCity());
    }

    @Override
    public ForecastHorly hourly(String city) {
        String units = "metric";
        Integer cnt = 15;
        String params = "q=" + city +
                "&units=" + units +
                "&lang=" + settings.getDefaultLang() +
                "&cnt=" + cnt +
                "&APPID=" + settings.getOpenweatherApiKey();
        return helper.doGetJson(FORECAST_URL, params, ForecastHorly.class);
    }

    @Override
    public ForecastDaily daily() {
        return daily(settings.getDefaultCity());
    }

    @Override
    public ForecastDaily daily(String city) {
        String units = "metric";
        Integer cnt = 15;
        String params = "q=" + city +
                "&units=" + units +
                "&lang=" + settings.getDefaultLang() +
                "&cnt=" + cnt +
                "&APPID=" + settings.getOpenweatherApiKey();
        return helper.doGetJson(FORECAST_URL_DAILY, params, ForecastDaily.class);
    }


}
