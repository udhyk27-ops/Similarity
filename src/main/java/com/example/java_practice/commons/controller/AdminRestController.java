package com.example.java_practice.commons.controller;

import java.util.ArrayList;

import com.example.java_practice.commons.dto.User;
import com.example.java_practice.commons.dto.WorkWithUser;
import com.example.java_practice.commons.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminRestController {

    private final AdminService adminService;

    // 기본정보 조회
    @GetMapping("/searchWork")
    public ArrayList<WorkWithUser> selWorkWithUser(String sort, int workNo) {

        ArrayList<WorkWithUser> list = adminService.selWorkWithUser(sort, workNo);
        System.out.println("searchWork : " + list);
        return list;
    }
    
    // 회원정보 조회 모달
    @GetMapping("/searchUser")
    public ArrayList<User> selUser(String sort) { return adminService.selUserList(sort); }

    // 회원 삭제
    @PostMapping("/deleteUser")
    public int delUser(int userNo) { return adminService.delUser(userNo); }

}
