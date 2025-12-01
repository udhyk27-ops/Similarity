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

//        System.out.println("awardSearch.filter : " + awardSearch.getFilter());

        // paging
        int limit = 5;
        int offset = (awardSearch.getPage() - 1) * limit;

        awardSearch.setLimit(limit);
        awardSearch.setOffset(offset);
        awardSearch.setSort("award");

        // LIST
        ArrayList<Award> awardList = adminService.selAwardList(awardSearch);
        int cdCnt = adminService.cntAwardList(awardSearch);

//        System.out.println(awardList);

        int totalCnt = adminService.cntAwardList(new AwardSearch(){{
            setSort("award");
        }}); // 전체 건수

        int totalPages = (int) Math.ceil((double) cdCnt / limit);

        model.addAttribute("filter", awardSearch.getFilter());

        model.addAttribute("awardSearch", awardSearch);
        model.addAttribute("awardList", awardList);

        model.addAttribute("currentPage", awardSearch.getPage() == 0 ? 1 : awardSearch.getPage());
        model.addAttribute("pageSize", limit);

        model.addAttribute("totalPages", totalPages);
        model.addAttribute("cdCnt", cdCnt);
        model.addAttribute("totalCnt", totalCnt);

        return "commons/admin/awardReg";
    }
    
    // 초대작 등록현황 페이지
    @GetMapping("invit")
    public String invitPage(@ModelAttribute AwardSearch invitSearch, Model model) {

        // paging
        int limit = 5;
        int offset = (invitSearch.getPage() - 1) * limit;

        invitSearch.setLimit(limit);
        invitSearch.setOffset(offset);
        invitSearch.setSort("invit");

        // LIST
        ArrayList<Award> invitList = adminService.selAwardList(invitSearch);
        int cdCnt = adminService.cntAwardList(invitSearch);

//        System.out.println("invitList : " + invitList);

        // COUNT
        int totalCnt = adminService.cntAwardList(new AwardSearch(){{
            setSort("invit");
        }}); // 전체 건수

        int totalPages = (int) Math.ceil((double) cdCnt / limit);

        model.addAttribute("filter", invitSearch.getFilter());

        model.addAttribute("invitSearch", invitSearch);
        model.addAttribute("invitList", invitList);

        model.addAttribute("currentPage", invitSearch.getPage() == 0 ? 1 : invitSearch.getPage());
        model.addAttribute("pageSize", limit);

        model.addAttribute("totalPages", totalPages);
        model.addAttribute("cdCnt", cdCnt);
        model.addAttribute("totalCnt", totalCnt);

        return "commons/admin/invitReg";
    }

    // 유사도 검색 페이지
    @GetMapping("similar")
    public String similarPage(){return "commons/admin/similar";}

    // 운영회원 관리 페이지
    @GetMapping("userManage")
    public String userManagePage(@ModelAttribute AwardSearch awardSearch, Model model) {

        // paging
        int limit = 10;
        int offset = (awardSearch.getPage() - 1) * limit;

        awardSearch.setLimit(limit);
        awardSearch.setOffset(offset);
        awardSearch.setSort("award");

        // LIST
        ArrayList<Award> awardList = adminService.selAwardList(awardSearch);
        int cdCnt = adminService.cntAwardList(awardSearch);

        // COUNT
        AwardSearch cntWorks = new AwardSearch();
        cntWorks.setSort("award");

        int totalCnt = adminService.cntAwardList(cntWorks); // 전체 건수
        int totalPages = (int) Math.ceil((double) cdCnt / limit);

        model.addAttribute("awardSearch", awardSearch);
        model.addAttribute("awardList", awardList);
        model.addAttribute("currentPage", awardSearch.getPage() == 0 ? 1 : awardSearch.getPage());
        model.addAttribute("pageSize", limit);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("cdCnt", cdCnt);
        model.addAttribute("totalCnt", totalCnt);

        return "commons/admin/userManage";
    }

    // 관리자 등록 관리 페이지
    @GetMapping("adminManage")
    public String adminManagePage(@ModelAttribute AwardSearch awardSearch, Model model){

        // paging
        int limit = 10;
        int offset = (awardSearch.getPage() - 1) * limit;

        awardSearch.setLimit(limit);
        awardSearch.setOffset(offset);
        awardSearch.setSort("award");

        // LIST
        ArrayList<Award> awardList = adminService.selAwardList(awardSearch);
        int cdCnt = adminService.cntAwardList(awardSearch);

        // COUNT
        AwardSearch cntWorks = new AwardSearch();
        cntWorks.setSort("award");

        int totalCnt = adminService.cntAwardList(cntWorks); // 전체 건수
        int totalPages = (int) Math.ceil((double) cdCnt / limit);

        model.addAttribute("awardSearch", awardSearch);
        model.addAttribute("awardList", awardList);
        model.addAttribute("currentPage", awardSearch.getPage() == 0 ? 1 : awardSearch.getPage());
        model.addAttribute("pageSize", limit);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("cdCnt", cdCnt);
        model.addAttribute("totalCnt", totalCnt);

        return "commons/admin/adminManage";
    }
}
