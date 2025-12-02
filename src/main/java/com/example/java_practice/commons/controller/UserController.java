package com.example.java_practice.commons.controller;

import com.example.java_practice.commons.dto.WorkSearch;
import com.example.java_practice.commons.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
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

    @GetMapping("/single/{type}")
    public String singleRegPage(@PathVariable("type") String type,
                                Model model)
    {
        model.addAttribute("type", type);
        if(type.equals("award")) {

        }else{

        }
        return "commons/user/singleRegPage";
    }

    @GetMapping("/bulk/{type}")
    public String bulkRegPage(@PathVariable("type") String type,
                              Model model)
    {
        model.addAttribute("type", type);
        if(type.equals("award")) {

        }else{

        }
        return "commons/user/bulkRegPage";
    }
}
