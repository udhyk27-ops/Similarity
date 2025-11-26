package com.example.java_practice.commons.controller;

import com.example.java_practice.commons.dto.Notice;
import com.example.java_practice.commons.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/notice")
    public String noticePage(
           Model model)
    {
        model.addAttribute("noticeList", noticeService.selNoticeList());

        return "commons/notice/noticePage";
    }

    @GetMapping("/noticeReg")
    public String noticeRegPage(){return "commons/notice/noticeRegPage";}

    @GetMapping("/detail")
    public String noticeDetailPage(){return "commons/notice/noticeDetailPage";}

    // 공지 등록
    @PostMapping("/regNoticeProcess")
    public String regNoticeProcess(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("noticeFiles")
            List<MultipartFile> noticeFiles,
            Notice params)
    {
        params.setF_regid(userDetails.getUsername());
        Notice notice = noticeService.insertNotice(params);

        boolean hasFile = noticeFiles.stream()
                .anyMatch(f -> f != null && !f.isEmpty() && f.getSize() > 0);


        if(hasFile) {
            noticeService.insertNoticeFile(notice.getF_id(), noticeFiles);
        }

        return "redirect:/notice";
    }
}
