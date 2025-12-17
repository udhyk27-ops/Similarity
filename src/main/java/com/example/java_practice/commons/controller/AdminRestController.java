package com.example.java_practice.commons.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.example.java_practice.commons.dto.Auth;
import com.example.java_practice.commons.dto.Similar;
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

    @GetMapping("/searchWork") // 기본정보 조회
    public ArrayList<WorkWithUser> selWorkWithUser(@RequestParam String sort, @RequestParam int workNo) { return adminService.selWorkWithUser(sort, workNo); }
    

    @GetMapping("/searchUser") // 회원정보 조회 모달
    public List<User> selUser(@RequestParam(required = false) String sort,
                              @RequestParam(required = false, defaultValue = "0") int userNo)
    { return adminService.selUserList(sort, userNo); }


    @PostMapping("/regWork") // 작품 등록
    public int regWork(@RequestParam String sort, @RequestParam int workNo) {

        Random r = new Random();
        int num = r.nextInt(1000000);
        String workCode = String.format("EMC%06d", num);

        return adminService.regWork(sort, workNo, workCode);
    }

    @PostMapping("/deleteWork") // 작품 삭제
    public int delWork(@RequestParam String sort, @RequestParam int workNo) { return adminService.delWork(sort, workNo); }

    @PostMapping("/deleteUser") // 회원 삭제
    public int delUser(@RequestParam String sort, @RequestParam int userNo) { return adminService.delUser(sort, userNo); }

    @PostMapping("/modifyInfo") // 정보 수정
    public int modInfo(
            @RequestParam ArrayList<String> work,
            @RequestParam ArrayList<String> user
    ) { return adminService.modInfo(work, user); }

    @PostMapping("/saveInfo") // 관리 - 저장
    public int saveInfo(@ModelAttribute User user,
                        @RequestParam(value = "auth", required = false) List<String> authLists) {
        Auth auth = new Auth();
        if (user.getF_sort().equals("관리자")) { auth = convertAuth(authLists); }
        if (user.getF_user_no() != null) { auth.setF_user_no(user.getF_user_no()); }

        return adminService.saveInfo(user, auth);
    }

    private Auth convertAuth(List<String> authCodes) {
        Auth auth = new Auth();

        List<String> list = authCodes == null ? Collections.emptyList() : authCodes;
        auth.setF_auth_award(list.contains("f_auth_award") ? "Y" : "N");
        auth.setF_auth_invit(list.contains("f_auth_invit") ? "Y" : "N");
        auth.setF_auth_reg(list.contains("f_auth_reg") ? "Y" : "N");
        auth.setF_auth_sim(list.contains("f_auth_sim") ? "Y" : "N");
        auth.setF_auth_user(list.contains("f_auth_user") ? "Y" : "N");
        auth.setF_auth_admin(list.contains("f_auth_admin") ? "Y" : "N");

        return auth;
    }

    @GetMapping("/compareImage")
    public Similar compareImage(@RequestParam String filename) { return adminService.compareImage(filename); }




}

