package com.example.java_practice.commons.controller;

import com.example.java_practice.commons.dto.NoticeSearch;
import com.example.java_practice.commons.service.NoticeService;
import com.example.java_practice.commons.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final NoticeService noticeService;
    private final UserService userService;

    @GetMapping("/main")
    public String dashboardPage(
            Model model,
            NoticeSearch params,
            @RequestParam(defaultValue = "1") int Page,
            @RequestParam(defaultValue = "5") int Size
    )
    {
        // 차트 추가하기
        model.addAttribute("noticeList", noticeService.selNoticeList(params, Page, Size));
        model.addAttribute("workStats", userService.selectWorkStats());
        return "commons/main/dashboardPage";
    }
}
