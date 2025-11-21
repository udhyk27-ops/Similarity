package com.example.java_practice.commons.controller;

import com.example.java_practice.commons.dto.Award;
import com.example.java_practice.commons.dto.AwardSearch;
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

        AwardSearch awardSearch = new AwardSearch();
        awardSearch.setPage(1);
        awardSearch.setSort("award");
        awardSearch.setKeyword("");
        awardSearch.setFilter("");
        awardSearch.setStartDate("");
        awardSearch.setEndDate("");

        ArrayList<Award> awardList = adminService.selectAwardList(awardSearch);
        model.addAttribute("awardList", awardList);

        return "commons/admin/awardReg";
    }

    @GetMapping("invit")
    public String invitPage() {

        AwardSearch awardSearch = new AwardSearch();
        awardSearch.setPage(1);
        awardSearch.setSort("invit");
        awardSearch.setKeyword("");
        awardSearch.setFilter("");
        awardSearch.setStartDate("");
        awardSearch.setEndDate("");


        return "admin/invitReg";
    }



}
