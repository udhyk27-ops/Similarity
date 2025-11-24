package com.example.java_practice.commons.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/worklist")
    public String artWorkListPage(){return "commons/user/artWorkListPage";}

    @GetMapping("/single")
    public String singleRegPage(){return "commons/user/singleRegPage";}

    @GetMapping("/bulk")
    public String bulkRegPage(){return "commons/user/bulkRegPage";}
}
