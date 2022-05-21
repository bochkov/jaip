package com.sergeybochkov.jaip.web.api;

import com.sergeybochkov.jaip.model.forecast.ForecastDaily;
import com.sergeybochkov.jaip.model.forecast.ForecastHourly;
import com.sergeybochkov.jaip.model.forecast.Weather;
import com.sergeybochkov.jaip.service.ForecastService;
import com.sergeybochkov.jaip.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public final class ForecastApi {

    private static final String DEFAULT_CITY = "Yekaterinburg,ru";

    private final WeatherService weatherService;
    private final ForecastService forecastService;

    @GetMapping("/weather/")
    public Weather weather() {
        return weatherService.get(DEFAULT_CITY);
    }

    @GetMapping("/weather/{city}/")
    public Weather weather(@PathVariable String city) {
        return weatherService.get(city, "ru");
    }

    @GetMapping("/forecast/")
    public ForecastHourly forecast() {
        return forecastService.hourly(DEFAULT_CITY);
    }

    @GetMapping("/forecast/{city}/")
    public ForecastHourly forecast(@PathVariable String city) {
        return forecastService.hourly(city);
    }

    @GetMapping("/forecast/daily/")
    public ForecastDaily forecastDaily() {
        return forecastService.daily(DEFAULT_CITY);
    }

    @GetMapping("/forecast/daily/{city}/")
    public ForecastDaily forecastDaily(@PathVariable String city) {
        return forecastService.daily(city);
    }
}
