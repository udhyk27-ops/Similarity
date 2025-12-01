package com.example.java_practice.commons.controller;

import com.example.java_practice.commons.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/worklist/{type}")
    public String artWorkListPage(@PathVariable("type") String type,
                                  Model model){
            model.addAttribute("type", type);
        if(type.equals("award")) {
            model.addAttribute("awardList", userService.selectList(type));
        }else{
            model.addAttribute("type", type);
        }
        return "commons/user/artWorkListPage";
    }

    @GetMapping("/single/{type}")
    public String singleRegPage(@PathVariable("type") String type,
                                Model model){
        model.addAttribute("type", type);
        if(type.equals("award")) {

        }else{

        }
        return "commons/user/singleRegPage";
    }

    @GetMapping("/bulk")
    public String bulkRegPage(){return "commons/user/bulkRegPage";}
}
