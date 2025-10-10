package com.sky.alcohol.web;

import com.sky.alcohol.app.AlcoholService;
import com.sky.alcohol.app.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/alcohol")
public class AlcoholController {

    private final AlcoholService alcoholService;  // 자유게시판
    private final ReviewService reviewService;    // 시음 후기

    // ===== 메인 =====
    @GetMapping("/main")
    public String main(Model model) {
        model.addAttribute("msg", "주류 커뮤니티 시작!");
        return "alcohol/main";
    }

    // ===== 자유게시판 =====
    @GetMapping("/board")
    public String board(Model model) {
        model.addAttribute("posts", alcoholService.findAllDesc());
        return "alcohol/board";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("post", alcoholService.getOne(id));
        return "alcohol/detail";
    }

    // 글쓰기 폼(자유게시판) - write.html 재사용
    @GetMapping("/write")
    public String write(Model model) {
        model.addAttribute("action", "/alcohol/write");     // POST 타깃
        model.addAttribute("back",   "/alcohol/board");     // 목록 버튼
        model.addAttribute("titleText", "자유게시판 글쓰기");
        return "alcohol/write";
    }

    // 글 저장(자유게시판)
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
        model.addAttribute("posts", reviewService.findAllDesc());
        return "alcohol/review";
    }

    @GetMapping("/review/{id}")
    public String reviewDetail(@PathVariable Long id, Model model) {
        model.addAttribute("post", reviewService.getOne(id));
        return "alcohol/review-detail";
    }

    // 글쓰기 폼(시음 후기) - write.html 재사용
    @GetMapping("/review/write")
    public String reviewWrite(Model model) {
        model.addAttribute("action", "/alcohol/review/write"); // POST 타깃
        model.addAttribute("back",   "/alcohol/review");       // 목록 버튼
        model.addAttribute("titleText", "시음 후기 글쓰기");
        return "alcohol/write";
    }

    // 글 저장(시음 후기)
    @PostMapping("/review/write")
    public String reviewWriteForm(@RequestParam("title") String title,
                                  @RequestParam("content") String content,
                                  @RequestParam("author") String author) {
        reviewService.write(title, content, author);
        return "redirect:/alcohol/review";
    }

    // 글 삭제
    @DeleteMapping("/board/{id}")
    public String deleteBoard(@PathVariable Long id) {
        alcoholService.delete(id);
        return "redirect:/alcohol/board";
    }

    @DeleteMapping("/review/{id}")
    public String deleteReview(@PathVariable Long id) {
        reviewService.delete(id);
        return "redirect:/alcohol/review";
    }

    // 도자기 굿즈 메뉴
    @GetMapping("/goods")
    public String goods(Model model) {
        model.addAttribute("titleText", "도자기 굿즈");
        // TODO: 추후 DB 연동. 지금은 화면만 확인
        return "alcohol/goods";
    }
}
