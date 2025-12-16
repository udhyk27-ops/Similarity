package com.example.java_practice.commons.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.java_practice.commons.dto.Auth;
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
    public ArrayList<WorkWithUser> selWorkWithUser(String sort, int workNo) { return adminService.selWorkWithUser(sort, workNo); }
    
    // 회원정보 조회 모달
    @GetMapping("/searchUser")
    public List<User> selUser(@RequestParam(required = false) String sort,
                              @RequestParam(required = false, defaultValue = "0") int userNo)
    { return adminService.selUserList(sort, userNo); }

    // 작품 삭제
    @PostMapping("/deleteWork")
    public int delWork(String sort, int workNo) { return adminService.delWork(sort, workNo); }

    // 회원 삭제
    @PostMapping("/deleteUser")
    public int delUser(String sort, int userNo) { return adminService.delUser(sort, userNo); }

    // 정보 수정
    @PostMapping("/modifyInfo")
    public int modInfo(
            @RequestParam ArrayList<String> work,
            @RequestParam ArrayList<String> user
    ) {
//        System.out.println("work : " + work);
//        System.out.println("user : " + user);
        return adminService.modInfo(work, user);
    }

    // 관리 - 저장
    @PostMapping("/saveInfo")
    public int saveInfo(@ModelAttribute User user,
                        @RequestParam(value = "auth", required = false) List<String> authLists) {

        Auth auth = new Auth();

        if (user.getF_user_no() != null) {
            auth = convertAuth(user.getF_user_no(), authLists);
            System.out.println("auth : " + auth);
        }

        System.out.println("saveInfo user : " + user);

        return adminService.saveInfo(user, auth);
    }

    private Auth convertAuth(int userNo, List<String> authCodes) {
        Auth auth = new Auth();
        auth.setF_user_no(userNo);

        List<String> list = authCodes == null ? Collections.emptyList() : authCodes;

        auth.setF_auth_award(list.contains("f_auth_award") ? "Y" : "N");
        auth.setF_auth_invit(list.contains("f_auth_invit") ? "Y" : "N");
        auth.setF_auth_reg(list.contains("f_auth_reg") ? "Y" : "N");
        auth.setF_auth_sim(list.contains("f_auth_sim") ? "Y" : "N");
        auth.setF_auth_user(list.contains("f_auth_user") ? "Y" : "N");
        auth.setF_auth_admin(list.contains("f_auth_admin") ? "Y" : "N");

        return auth;
    }



}
