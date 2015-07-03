package com.sergeybochkov.jaip.web;

import com.sergeybochkov.jaip.model.*;
import com.sergeybochkov.jaip.model.cbr.Valute;
import com.sergeybochkov.jaip.model.cbr.ValuteRate;
import com.sergeybochkov.jaip.model.forecast.ForecastDaily;
import com.sergeybochkov.jaip.model.forecast.ForecastHorly;
import com.sergeybochkov.jaip.model.forecast.Weather;
import com.sergeybochkov.jaip.model.news.News;
import com.sergeybochkov.jaip.model.pdf.Compress;
import com.sergeybochkov.jaip.model.pdf.Merge;
import com.sergeybochkov.jaip.model.pdf.Split;
import com.sergeybochkov.jaip.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
    @Autowired
    private NewsService newsService;
    @Autowired
    private PdfService pdfService;

    @RequestMapping("/forismatic/")
    public @ResponseBody Cite forismatic() {
        return citeService.get();
    }

    @RequestMapping("/weather/")
    public @ResponseBody
    Weather weather() {
        return weatherService.get();
    }

    @RequestMapping("/weather/{city}/")
    public @ResponseBody Weather weather(@PathVariable String city) {
        return weatherService.get(city);
    }

    @RequestMapping("/forecast/")
    public @ResponseBody ForecastHorly forecast() {
        return forecastService.get();
    }

    @RequestMapping("/forecast/{city}/")
    public @ResponseBody ForecastHorly forecast(@PathVariable String city) {
        return forecastService.get(city);
    }

    @RequestMapping("/forecast/daily/")
    public @ResponseBody
    ForecastDaily forecastDaily() {
        return forecastService.getDaily();
    }

    @RequestMapping("/forecast/daily/{city}/")
    public @ResponseBody
    ForecastDaily forecastDaily(@PathVariable String city) {
        return forecastService.getDaily(city);
    }

    @RequestMapping("/cbr/valutes/")
    public @ResponseBody List<Valute> valutes() {
        return cbrService.getValutes();
    }

    @RequestMapping("/cbr/rate/{valuteId}/")
    public @ResponseBody
    ValuteRate valuteRate(@PathVariable String valuteId) {
        return cbrService.getValuteRate(valuteId);
    }

    @RequestMapping("/cbr/rates/{valuteId}/")
    public @ResponseBody ValuteRate periodValuteRates(@PathVariable String valuteId) {
        return cbrService.getPeriodValuteRate(valuteId);
    }

    @RequestMapping("/news/{slug}/")
    public @ResponseBody List<News> news(@PathVariable String slug) {
        return newsService.getLatest(slug);
    }

    // curl http://127.0.0.1:8080/pdf/split/ -F "file=@...pdf" -F "pages=1,3" -F "singleFile=true"
    @RequestMapping(value = "/pdf/split/", method = RequestMethod.POST)
    public @ResponseBody Split split(@Valid Split split) {
        return pdfService.split(split.getFile(), split.getPages(), split.getSingleFile());
    }

    // curl http://127.0.0.1:8080/pdf/merge/ -F "files=@1.pdf" -F "files=@3.pdf"
    @RequestMapping(value = "/pdf/merge/", method = RequestMethod.POST)
    public @ResponseBody Merge merge(@Valid Merge merge) {
        return pdfService.merge(merge.getFiles());
    }

    // curl http://127.0.0.1:8080/pdf/compress/ -F "file=@20141107.pdf"
    @RequestMapping(value = "/pdf/compress/", method = RequestMethod.POST)
    public @ResponseBody Compress compress(@Valid Compress compress) {
        return pdfService.compress(compress.getFile());
    }
}
