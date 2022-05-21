package com.sergeybochkov.jaip.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public final class WebController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/weather/")
    public String weather() {
        return "weather";
    }

    @GetMapping("/exchange/")
    public String exchange() {
        return "exchange";
    }

    @GetMapping("/news/")
    public String news() {
        return "news";
    }

    @GetMapping("/about/")
    public String about() {
        return "about";
    }
}
