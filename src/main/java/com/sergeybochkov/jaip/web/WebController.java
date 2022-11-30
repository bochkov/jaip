package com.sergeybochkov.jaip.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public final class WebController {

    @ModelAttribute("requestUri")
    public String requestUri(final HttpServletRequest request) {
        return request.getRequestURI();
    }

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
