package com.example.java_practice.commons.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
    @GetMapping("/login")
    public String testLoginPage(){return "commons/test/testLoginPage";}
}