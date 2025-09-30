package com.sky.alcohol.web;

import com.sky.alcohol.app.AlcoholService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/alcohol")
public class AlcoholController {

    private final AlcoholService alcoholService; // ← final로 주입

    @GetMapping("/main")
    public String main(Model model) {
        model.addAttribute("msg", "주류 커뮤니티 시작!");
        return "alcohol/main";
    }

    // 목록 조회해서 모델에 넣기
    @GetMapping("/board")
    public String board(Model model) {
        var posts = alcoholService.findAllDesc(); // 최신순
        model.addAttribute("posts", posts);
        return "alcohol/board";
    }

    // 글쓰기 페이지
    @GetMapping("/write")
    public String write(Model model) {
        model.addAttribute("msg", "글쓰기 ㅋ");
        return "alcohol/write";
    }

    // 글쓰기 저장
    @PostMapping("/write")
    public String writeForm(@RequestParam("title") String title,
                            @RequestParam("content") String content,
                            @RequestParam("author") String author) {
        alcoholService.write(title, content, author);
        return "redirect:/alcohol/board";
    }

    // 시음 후기
    @GetMapping("/review")
    public String review(Model model) {
        model.addAttribute("msg", "시음 후기 ㅋ");
        return "alcohol/review";
    }

    // 글 상세 내용
    @GetMapping("/board/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        var post = alcoholService.getOne(id);
        model.addAttribute("post", post);
        return "alcohol/detail";
    }

}
