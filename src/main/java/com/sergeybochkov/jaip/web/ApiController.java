package com.sergeybochkov.jaip.web;

import com.sergeybochkov.jaip.model.Cite;
import com.sergeybochkov.jaip.model.cbr.Valute;
import com.sergeybochkov.jaip.model.cbr.ValuteRate;
import com.sergeybochkov.jaip.model.forecast.ForecastDaily;
import com.sergeybochkov.jaip.model.forecast.ForecastHorly;
import com.sergeybochkov.jaip.model.forecast.Weather;
import com.sergeybochkov.jaip.service.CbrService;
import com.sergeybochkov.jaip.service.CiteService;
import com.sergeybochkov.jaip.service.ForecastService;
import com.sergeybochkov.jaip.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private CiteService citeService;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private ForecastService forecastService;
    @Autowired
    private CbrService cbrService;

    @RequestMapping("/forismatic/")
    @ResponseBody
    public Cite forismatic() {
        return citeService.get();
    }

    @RequestMapping("/weather/")
    @ResponseBody
    public Weather weather() {
        return weatherService.get();
    }

    @RequestMapping("/weather/{city}/")
    @ResponseBody
    public Weather weather(@PathVariable String city) {
        return weatherService.get(city);
    }

    @RequestMapping("/forecast/")
    @ResponseBody
    public ForecastHorly forecast() {
        return forecastService.hourly();
    }

    @RequestMapping("/forecast/{city}/")
    @ResponseBody
    public ForecastHorly forecast(@PathVariable String city) {
        return forecastService.hourly(city);
    }

    @RequestMapping("/forecast/daily/")
    @ResponseBody
    public ForecastDaily forecastDaily() {
        return forecastService.daily();
    }

    @RequestMapping("/forecast/daily/{city}/")
    @ResponseBody
    public ForecastDaily forecastDaily(@PathVariable String city) {
        return forecastService.daily(city);
    }

    @RequestMapping("/cbr/valutes/")
    @ResponseBody
    public List<Valute> valutes() {
        return cbrService.getValutes();
    }

    @RequestMapping("/cbr/rate/{valuteId}/")
    @ResponseBody
    public ValuteRate valuteRate(@PathVariable String valuteId) {
        return cbrService.getValuteRate(valuteId);
    }

    @RequestMapping("/cbr/rates/{valuteId}/")
    @ResponseBody
    public ValuteRate periodValuteRates(@PathVariable String valuteId) {
        return cbrService.getPeriodValuteRate(valuteId);
    }
}
