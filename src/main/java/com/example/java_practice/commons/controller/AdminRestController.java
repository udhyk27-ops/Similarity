package com.example.java_practice.commons.controller;

import java.util.ArrayList;
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
    @GetMapping("/search")
    public ArrayList<Award> searchAward(@ModelAttribute AwardSearch awardSearch) {





        return adminService.selAwardList(awardSearch);
    }
    
    // 회원정보 조회 모달
    @GetMapping("/searchUser")
    public ArrayList<User> selUser(@ModelAttribute User user) { return adminService.selUserList(user); }

}
