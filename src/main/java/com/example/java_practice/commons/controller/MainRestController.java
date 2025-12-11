package com.example.java_practice.commons.controller;

import com.example.java_practice.commons.dto.*;
import com.example.java_practice.commons.service.AuthService;
import com.example.java_practice.commons.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/main/*")
@RequiredArgsConstructor
@Slf4j
public class MainRestController {

    private final UserService userService;
    private final AuthService authService;

    @GetMapping("/doughnut-chart")
    public ResponseEntity<?> getDouChartData(){
        TotalWorkStats totalWorkStats = userService.selectWorkStats();
        if(totalWorkStats == null) return ResponseEntity.badRequest().body(Map.of("status", false, "msg", "문제가 발생했습니다"));
        return ResponseEntity.ok().body(Map.of("status", true, "data", totalWorkStats));
    }
    @GetMapping("/pie-chart")
    public ResponseEntity<?> getPieChartData(){
        UserStats userStats = authService.selectUserSortStats();
        if(userStats == null) return ResponseEntity.badRequest().body(Map.of("status", false, "msg", "문제가 발생했습니다"));
        return ResponseEntity.ok().body(Map.of("status", true, "data", userStats));
    }
    @GetMapping("/bar-chart")
    public ResponseEntity<?> getBarChartData(){
        List<UserRegStats> userRegStats = authService.selectUserRegStats();
        if(userRegStats == null) return ResponseEntity.badRequest().body(Map.of("status", false, "msg", "문제가 발생했습니다"));
        return ResponseEntity.ok().body(Map.of("status", true, "data", userRegStats));
    }
    @GetMapping("/line-chart")
    public ResponseEntity<?> getLineChartData(){
        List<WorkYearStats> workStatsList = userService.selectYearStats();
        if(workStatsList == null) return ResponseEntity.badRequest().body(Map.of("status", false, "msg", "문제가 발생했습니다"));
        return ResponseEntity.ok().body(Map.of("status", true, "data", workStatsList));
    }
}
