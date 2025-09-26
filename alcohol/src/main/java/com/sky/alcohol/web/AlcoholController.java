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

    // main 페이지 (메뉴 보여주는 용도 ㅋ)
    @GetMapping("/main")
    public String main(Model model) {
        model.addAttribute("msg", "주류 커뮤니티 시작!");
        return "alcohol/main";
    }

    // 자유게시판
    @GetMapping("/board")
    public String board(Model model) {
        model.addAttribute("msg", "자유게시판ㅋ");
        return "alcohol/board";
    }
}
