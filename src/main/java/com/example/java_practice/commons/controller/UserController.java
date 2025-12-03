package com.example.java_practice.commons.controller;

import com.example.java_practice.commons.Enums.WorkExcelType;
import com.example.java_practice.commons.dto.Award;
import com.example.java_practice.commons.dto.Invit;
import com.example.java_practice.commons.dto.WorkSearch;
import com.example.java_practice.commons.security.CustomUserDetails;
import com.example.java_practice.commons.service.UserService;
import com.example.java_practice.commons.utils.CreateExcel;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.Year;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/worklist/{type}")
    public String artWorkListPage(
            Model model,
            @PathVariable("type") String type,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            WorkSearch params)
    {
        if(params.getStaDate() == null) params.setStaDate("");
        if(params.getEndDate() == null) params.setEndDate("");
        if(params.getStaYear() == null) params.setStaYear("");
        if(params.getEndYear() == null) params.setEndYear("");
        if(params.getSchKeyword() == null) params.setSchKeyword("");
        if(params.getKeyword() == null) params.setKeyword("");

        int searchCnt = userService.selectListSearchCnt(type, params);
        int totalPages = (int) Math.ceil((double) searchCnt / size);
        totalPages = totalPages == 0 ? 1 : totalPages;

        model.addAttribute("type", type);
        model.addAttribute("yearList", userService.selectYearList(type));
        model.addAttribute("workList", userService.selectList(type, params, page, size));
        model.addAttribute("totalCnt", userService.selectListTotalCnt(type));
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("search", params);
        model.addAttribute("searchCnt", searchCnt);

        return "commons/user/artWorkListPage";
    }

    @GetMapping("/workList/{type}/excel")
    public void downloadExcel(@PathVariable("type") String type,
                              WorkSearch params,
                              HttpServletResponse response)
    {

        WorkExcelType workExcelType = WorkExcelType.fromString(type);
        String sheetName = workExcelType.getSheetName();
        String[] headers = workExcelType.getHeaders();
        String[] fieldNames = workExcelType.getFieldNames();

        try{
            List<?> workList = userService.selectListForExcel(type, params);
            Workbook workbook = CreateExcel.createWorkListExcel(sheetName, headers, fieldNames, workList);
            String fileName = sheetName + "_" + System.currentTimeMillis() + ".xlsx";
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(fileName.getBytes("KSC5601"), "8859_1"));

            workbook.write(response.getOutputStream());
            workbook.close();

        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }

    @GetMapping("/single/{type}")
    public String singleRegPage(@PathVariable("type") String type,
                                Model model)
    {
        int currentYear = Year.now().getValue();
        model.addAttribute("type", type);
        model.addAttribute("currentYear", currentYear);
        return "commons/user/singleRegPage";
    }

    @PostMapping("/regSingleProcess")
    public String regSingleProcess(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam("type") String type,
            @RequestParam("file") MultipartFile file,
            @RequestParam("size_wid") String size_wid,
            @RequestParam("size_hei") String size_hei,
            Award awardParams,
            Invit invitParams)
    {
        if(type.equals("award")){
            awardParams.setF_user_no(userDetails.getUserNo());
            awardParams.setF_work_size(size_wid + "x" + size_hei);

            userService.insertSingleAwardWork(awardParams, file);
        }else{
            invitParams.setF_user_no(userDetails.getUserNo());
            invitParams.setF_work_size(size_wid + "x" + size_hei);

            userService.insertSingleInvitWork(invitParams, file);
        }
        return "redirect:/worklist/award";
    }

    @GetMapping("/bulk/{type}")
    public String bulkRegPage(@PathVariable("type") String type,
                              Model model)
    {
        model.addAttribute("type", type);
        return "commons/user/bulkRegPage";
    }
}
