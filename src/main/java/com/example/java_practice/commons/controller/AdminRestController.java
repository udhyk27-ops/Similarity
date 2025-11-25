package com.example.java_practice.commons.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.java_practice.commons.dto.Award;
import com.example.java_practice.commons.dto.AwardSearch;
import com.example.java_practice.commons.dto.User;
import com.example.java_practice.commons.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminRestController {

    private final AdminService adminService;

    /**
     * 리스트 검색
     *
     * sort => 수상작 / 초대작 구분
     * page => 페이징
     * keyword => 검색어
     * filter => select box 필터
     * startDate => 시작일자
     * endDate => 종료일자
     */
//    @GetMapping("/search")
//    public Map<String, Object> searchAward(@ModelAttribute AwardSearch awardSearch) {
//
//        System.out.println(awardSearch);
//
//        // paging
//        int limit = 5;
//        int offset = (awardSearch.getPage() - 1) * limit;
//
//        awardSearch.setLimit(limit);
//        awardSearch.setOffset(offset);
//
//        ArrayList<Award> awardList = adminService.selAwardList(awardSearch);
//
//
//        int totalCount = adminService.cntAwardList();
//        int totalPage = (int) Math.ceil((double) totalCount / limit);
//
//        Map<String, Object> result = new HashMap<>();
//        result.put("list", awardList);
//        result.put("currentPage", awardSearch.getPage());
//        result.put("totalPage", totalPage);
//        result.put("limit", limit);
//
//        System.out.println("result : " + result);
//
//        return result;
//    }
    
    // 회원정보 조회 모달
    @GetMapping("/searchUser")
    public ArrayList<User> selUser(@ModelAttribute User user) { return adminService.selUserList(user); }

}
