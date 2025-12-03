package com.example.java_practice.commons.controller;

import com.example.java_practice.commons.dto.Award;
import com.example.java_practice.commons.dto.Search;
import com.example.java_practice.commons.dto.User;
import com.example.java_practice.commons.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

    @RequestMapping("/**")
    public String handleUnknown() { // 404
        return "commons/admin/error";
    }

    private final AdminService adminService;

    // 수상작 / 초대작 등록현황
    @GetMapping("{type:award|invit}")
    public String awardOrInvitPage(@PathVariable String type, @ModelAttribute Search search, Model model) {

        int limit = 5;
        int offset = (search.getPage() - 1) * limit;

        search.setLimit(limit);
        search.setOffset(offset);
        search.setSort(type);

        ArrayList<Award> list = adminService.selAwardList(search);
        int cdCnt = adminService.cntAwardList(search);
        int totalCnt = adminService.cntAwardList(new Search() {{ setSort(type); }}); // 전체 건수

        model.addAttribute("filter", search.getFilter());
        model.addAttribute(type + "Search", search); // awardSearch / invitSearch
        model.addAttribute(type + "List", list); // awardList / invitList
        model.addAttribute("currentPage", search.getPage() == 0 ? 1 : search.getPage());
        model.addAttribute("pageSize", limit);
        model.addAttribute("totalPages", (int) Math.ceil((double) cdCnt / limit));
        model.addAttribute("cdCnt", cdCnt);
        model.addAttribute("totalCnt", totalCnt);

        return "commons/admin/" + (type.equals("award") ? "awardReg" : "invitReg");
    }

    // 유사도 검색 페이지
    @GetMapping("similar")
    public String similarPage(){return "commons/admin/similar";}

    // 운영회원 / 관리자 관리 페이지
    @GetMapping("{type:user|manage}")
    public String userManagePage(@PathVariable String type, @ModelAttribute Search userSearch, Model model) {

        System.out.println("type : " + type);

        userSearch.setSort(type.equals("user") ? "회원" : "관리자");

        // paging
        int limit = 10;
        int offset = (userSearch.getPage() - 1) * limit;
        userSearch.setLimit(limit);
        userSearch.setOffset(offset);

        ArrayList<User> userList = adminService.selManageList(userSearch);
        int userCnt = adminService.cntUserList(userSearch);
        int totalCnt = adminService.cntUserList(new Search(){{ setSort(type.equals("user") ? "회원" : "관리자"); }}); // 전체 건수

        System.out.println("userList : " + userList);

        model.addAttribute("userSearch", userSearch);
        model.addAttribute("userList", userList);
        model.addAttribute("currentPage", userSearch.getPage() == 0 ? 1 : userSearch.getPage());
        model.addAttribute("pageSize", limit);
        model.addAttribute("totalPages", (int) Math.ceil((double) userCnt / limit));
        model.addAttribute("userCnt", userCnt);
        model.addAttribute("totalCnt", totalCnt);

        return type.equals("user")
            ? "commons/admin/userManage"
            : "commons/admin/adminManage";
    }

}
