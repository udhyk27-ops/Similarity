package com.example.java_practice.commons.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NoticeController {
    @GetMapping("/notice")
    public String noticePage(){return "commons/notice/noticePage";}

    @GetMapping("/noticeReg")
    public String noticeRegPage(){return "commons/notice/noticeRegPage";}
}
