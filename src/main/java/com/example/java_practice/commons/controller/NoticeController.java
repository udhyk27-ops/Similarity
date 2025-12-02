package com.example.java_practice.commons.controller;

import com.example.java_practice.commons.dto.Notice;
import com.example.java_practice.commons.dto.NoticeSearch;
import com.example.java_practice.commons.security.CustomUserDetails;
import com.example.java_practice.commons.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/notice")
    public String noticePage(
           Model model,
           @AuthenticationPrincipal CustomUserDetails userDetails,
           @RequestParam(defaultValue = "1") int page,
           @RequestParam(defaultValue = "10") int size,
           NoticeSearch params)
    {
        if (params.getKeyword() == null) params.setKeyword("");
        if (params.getStaDate() == null) params.setStaDate("");
        if (params.getEndDate() == null) params.setEndDate("");

        int totalCnt = noticeService.selNoticeCnt(params);
        int totalPages = (int) Math.ceil((double) totalCnt / size);
        totalPages = totalPages == 0 ? 1 : totalPages;

        model.addAttribute("user", userDetails);
        model.addAttribute("noticeList", noticeService.selNoticeList(params, page, size));
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("search", params);

        return "commons/notice/noticePage";
    }

    @GetMapping({"/noticeReg", "/noticeReg/{id}"})
    public String noticeRegPage(
            @PathVariable(name="id", required = false) Integer id,
            Model model)
    {
        if(id != null) {
            model.addAttribute("noticeDetail", noticeService.selectNoticeDetailById(id));
            model.addAttribute("noticeFiles", noticeService.selectNoticeFilesByNoticeId(id));
        }else{
            model.addAttribute("noticeDetail", new Notice());
            model.addAttribute("noticeFiles", List.of());
        }
        return "commons/notice/noticeRegPage";
    }

    @GetMapping("/detail/{id}")
    public String noticeDetailPage(
            @PathVariable int id,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model)
    {
        model.addAttribute("user", userDetails);
        model.addAttribute("noticeDetail", noticeService.selectNoticeDetailById(id));
        model.addAttribute("noticeFiles", noticeService.selectNoticeFilesByNoticeId(id));
        return "commons/notice/noticeDetailPage";
    }

    // 공지 수정, 등록
    @PostMapping("/regNoticeProcess")
    public String regNoticeProcess(
            @ModelAttribute("noticeDetail") Notice noticeDetail,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam("noticeFiles") List<MultipartFile> noticeFiles,
            @RequestParam(required = false) String deleteFileIds,
            Notice params)
    {

        Notice notice;

        // 수정
        if(noticeDetail.getF_id() > 0){
            params.setF_modiid(userDetails.getUserNo());
            notice = noticeService.updateNoticeById(params);

            // 파일 삭제
            if(deleteFileIds != null && !deleteFileIds.isEmpty()){
                String[] fileIds = deleteFileIds.split(",");
                for(String fileId : fileIds){
                    noticeService.deleteNoticeFilesById(Integer.parseInt(fileId));
                }
            }

        }else{
            // 등록
            params.setF_regid(userDetails.getUserNo());
            notice = noticeService.insertNotice(params);
        }

        // 신규 파일 등록
        boolean hasFile = noticeFiles.stream()
                .anyMatch(f -> f != null && !f.isEmpty() && f.getSize() > 0);

        if(hasFile) {
            noticeService.insertNoticeFile(notice.getF_id(), noticeFiles);
        }

        return "redirect:/notice";
    }
}
