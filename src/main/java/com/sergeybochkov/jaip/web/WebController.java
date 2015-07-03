package com.sergeybochkov.jaip.web;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class WebController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/weather/")
    public String weather(){
        return "weather";
    }

    @RequestMapping("/exchange/")
    public String exchange(){
        return "exchange";
    }

    @RequestMapping("/news/")
    public String news(){
        return "news";
    }

    @RequestMapping("/about/")
    public String about(){
        return "about";
    }
}
