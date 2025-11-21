package com.example.java_practice.commons.controller;

import com.example.java_practice.commons.dto.Award;
import com.example.java_practice.commons.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import org.springframework.ui.Model;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // 수상작 등록현황 페이지
    @GetMapping("award")
    public String awardPage(Model model) {

        ArrayList<Award> awardList = adminService.selectAwardList();
        model.addAttribute("awardList", awardList);

        return "commons/admin/awardReg";
    }

    @GetMapping("invit")
    public String invitPage() {return "admin/invitReg";}



}
