package com.sky.alcohol.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/alcohol")
public class AlcoholController {

    @GetMapping("/main")
    public String main(Model model) {
        model.addAttribute("msg", "주류 커뮤니티 시작!");
        return "alcohol/main"; // templates/alcohol/main.html
    }
}
