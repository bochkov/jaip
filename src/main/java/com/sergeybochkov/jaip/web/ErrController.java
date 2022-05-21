package com.sergeybochkov.jaip.web;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
public final class ErrController extends BasicErrorController {

    private static final String ERROR_PATH = "/error/";

    public ErrController(ErrorAttributes errorAttributes, ServerProperties serverProperties) {
        super(errorAttributes, serverProperties.getError());
    }

    @RequestMapping(value = ERROR_PATH)
    public String error() {
        return "404";
    }
}
