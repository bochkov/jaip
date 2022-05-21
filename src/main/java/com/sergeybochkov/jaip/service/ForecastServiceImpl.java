package com.sergeybochkov.jaip.service;

import com.sergeybochkov.jaip.helper.Resource;
import com.sergeybochkov.jaip.model.forecast.ForecastDaily;
import com.sergeybochkov.jaip.model.forecast.ForecastHourly;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class ForecastServiceImpl implements ForecastService {

    private final Resource resource;

    @Value("${openweather.api.key}")
    private String apiKey;

    @Override
    public ForecastHourly hourly(String city) {
        return hourly(city, "ru");
    }

    @Override
    public ForecastHourly hourly(String city, String lang) {
        String units = "metric";
        int cnt = 15;
        String params = "q=" + city +
                "&units=" + units +
                "&lang=" + lang +
                "&cnt=" + cnt +
                "&APPID=" + apiKey;
        return resource.getJson(FORECAST_URL, params, ForecastHourly.class);
    }

    @Override
    public ForecastDaily daily(String city) {
        return daily(city, "ru");
    }

    @Override
    public ForecastDaily daily(String city, String lang) {
        String units = "metric";
        int cnt = 15;
        String params = "q=" + city +
                "&units=" + units +
                "&lang=" + lang +
                "&cnt=" + cnt +
                "&APPID=" + apiKey;
        return resource.getJson(FORECAST_URL_DAILY, params, ForecastDaily.class);
    }

}
