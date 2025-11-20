package com.example.java_practice.commons.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {

    @GetMapping("award")
    public String awardPage() {return "admin/awardReg";}

    @GetMapping("invit")
    public String invitPage() {return "admin/invitReg";}



}
