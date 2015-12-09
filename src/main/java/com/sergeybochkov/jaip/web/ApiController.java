package com.sergeybochkov.jaip.web;

import com.sergeybochkov.jaip.model.Cite;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
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

    @RequestMapping("/news/{slug}/")
    @ResponseBody
    public List<News> news(@PathVariable String slug) {
        return newsService.getLatest(slug);
    }

    // curl http://127.0.0.1:8080/pdf/split/ -F "file=@...pdf" -F "pages=1,3" -F "singleFile=true"
    @RequestMapping(value = "/pdf/split/", method = RequestMethod.POST)
    @ResponseBody
    public Split split(@Valid Split split) {
        return pdfService.split(split.getFile(), split.getPages(), split.getSingleFile());
    }

    // curl http://127.0.0.1:8080/pdf/merge/ -F "files=@1.pdf" -F "files=@3.pdf"
    @RequestMapping(value = "/pdf/merge/", method = RequestMethod.POST)
    @ResponseBody
    public Merge merge(@Valid Merge merge) {
        return pdfService.merge(merge.getFiles());
    }

    // curl http://127.0.0.1:8080/pdf/compress/ -F "file=@20141107.pdf"
    @RequestMapping(value = "/pdf/compress/", method = RequestMethod.POST)
    @ResponseBody
    public Compress compress(@Valid Compress compress) {
        return pdfService.compress(compress.getFile());
    }
}
