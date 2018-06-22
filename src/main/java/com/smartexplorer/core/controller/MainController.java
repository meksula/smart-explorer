package com.smartexplorer.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author
 * Karol Meksuła
 * 22-06-2018
 * */

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping
    public String homePage() {
        return "home";
    }

}
