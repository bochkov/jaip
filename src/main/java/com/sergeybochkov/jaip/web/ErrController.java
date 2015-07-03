package com.sergeybochkov.jaip.web;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrController implements ErrorController {

    private static final String errorPath = "/error/";

    @RequestMapping(value = errorPath)
    public String error(){
        return "404";
    }

    @Override
    public String getErrorPath() {
        return errorPath;
    }
}
