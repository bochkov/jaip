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

    @Override
    public ForecastHorly get() {
        return get(Settings.DEFAULT_CITY);
    }

    @Override
    public ForecastHorly get(String city) {
        String units = "metric";
        Integer cnt = 5;
        String params = "q=" + city + "&units=" + units + "&lang=" + Settings.DEFAULT_LANG + "&cnt=" + cnt;
        return helper.doGetJson(FORECAST_URL, params, ForecastHorly.class);
    }

    @Override
    public ForecastDaily getDaily() {
        return getDaily(Settings.DEFAULT_CITY);
    }

    @Override
    public ForecastDaily getDaily(String city) {
        String units = "metric";
        Integer cnt = 14;
        String params = "q=" + city + "&units=" + units + "&lang=" + Settings.DEFAULT_LANG + "&cnt=" + cnt;
        return helper.doGetJson(FORECAST_URL_DAILY, params, ForecastDaily.class);
    }


}
