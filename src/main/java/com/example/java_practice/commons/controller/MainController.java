package com.example.java_practice.commons.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/main")
    public String dashboardPage(){return "commons/main/dashboardPage";}
}
