package com.smartexplorer.core.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author
 * Karol Meksuła
 * 25-06-2018
 * */

@RestController
public class MainController implements ErrorController {
    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    @ResponseStatus(HttpStatus.OK)
    public String error() {
        return "There is no such endpoint. Look at documentation: http://51.38.129.50:4000/";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

}
