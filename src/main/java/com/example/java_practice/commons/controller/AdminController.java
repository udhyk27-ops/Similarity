package com.example.java_practice.commons.controller;

import com.example.java_practice.commons.dto.Award;
import com.example.java_practice.commons.dto.AwardSearch;
import com.example.java_practice.commons.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // 수상작 등록현황 페이지
    @GetMapping("award")
    public String awardPage(@ModelAttribute AwardSearch awardSearch, Model model) {

        // paging
        int limit = 5;
        int offset = (awardSearch.getPage() - 1) * limit;

        awardSearch.setLimit(limit);
        awardSearch.setOffset(offset);
        awardSearch.setSort("award");

        ArrayList<Award> awardList = adminService.selAwardList(awardSearch);
        int cdCnt = adminService.cntAwardList(awardSearch);
        int totalCnt = adminService.cntAwardList(new AwardSearch()); // 전체 건수
        int totalPages = (int) Math.ceil((double) cdCnt / limit);

        model.addAttribute("awardSearch", awardSearch);
        model.addAttribute("awardList", awardList);
        model.addAttribute("currentPage", awardSearch.getPage());
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("cdCnt", cdCnt);
        model.addAttribute("totalCnt", totalCnt);

        return "commons/admin/awardReg";
    }

    @GetMapping("invit")
    public String invitPage(@ModelAttribute AwardSearch awardSearch, Model model) {

        // paging
        int limit = 5;
        int offset = (awardSearch.getPage() - 1) * limit;

        awardSearch.setLimit(limit);
        awardSearch.setOffset(offset);
        awardSearch.setSort("award");

        ArrayList<Award> awardList = adminService.selAwardList(awardSearch);
        int cdCnt = adminService.cntAwardList(awardSearch);
        int totalCnt = adminService.cntAwardList(new AwardSearch()); // 전체 건수
        int totalPages = (int) Math.ceil((double) cdCnt / limit);

        model.addAttribute("awardSearch", awardSearch);
        model.addAttribute("awardList", awardList);
        model.addAttribute("currentPage", awardSearch.getPage());
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("cdCnt", cdCnt);
        model.addAttribute("totalCnt", totalCnt);

        return "commons/admin/invitReg";
    }



}
