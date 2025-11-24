package com.example.java_practice.commons.controller;

import com.example.java_practice.commons.dto.Award;
import com.example.java_practice.commons.dto.AwardSearch;
import com.example.java_practice.commons.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // 수상작 등록현황 페이지
    @GetMapping("award")
    public String awardPage(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                            Model model) {
        // paging
        int limit = 5;
        int offset = (page - 1) * limit;

        AwardSearch awardSearch = new AwardSearch();

        awardSearch.setLimit(limit);
        awardSearch.setOffset(offset);

        awardSearch.setSort("award");
        awardSearch.setKeyword("");
        awardSearch.setFilter("");
        awardSearch.setStartDate("");
        awardSearch.setEndDate("");

        ArrayList<Award> awardList = adminService.selAwardList(awardSearch);
        int total = adminService.cntAwardList();
        int totalPages = (int) Math.ceil((double) total / limit);

        model.addAttribute("awardList", awardList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("total", total);

        return "commons/admin/awardReg";
    }

    @GetMapping("invit")
    public String invitPage() {

        AwardSearch awardSearch = new AwardSearch();
        awardSearch.setOffset(1);

        awardSearch.setSort("invit");
        awardSearch.setKeyword("");
        awardSearch.setFilter("");
        awardSearch.setStartDate("");
        awardSearch.setEndDate("");


        return "admin/invitReg";
    }



}
