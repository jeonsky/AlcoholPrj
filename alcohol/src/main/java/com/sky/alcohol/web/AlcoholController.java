package com.sky.alcohol.web;

import com.sky.alcohol.app.AlcoholService;
import com.sky.alcohol.app.ReviewService;
import com.sky.alcohol.domain.Alcohol;
import com.sky.alcohol.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/alcohol")
public class AlcoholController {

    private final AlcoholService alcoholService;
    private final ReviewService reviewService;

    // ===== 메인 =====
    @GetMapping("/main")
    public String main(Model model) {
        model.addAttribute("msg", "주류 커뮤니티 시작!");
        return "alcohol/main";
    }

    // ===== 자유게시판 목록 =====
    @GetMapping("/board")
    public String board(Model model) {
        model.addAttribute("posts", alcoholService.findAllDesc());
        return "alcohol/board";
    }

    // ===== 자유게시판 상세 =====
    @GetMapping("/board/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("post", alcoholService.getOne(id));
        return "alcohol/detail";
    }

    // ===== 자유게시판 글 작성 =====
    @GetMapping("/write")
    public String write(Model model) {
        model.addAttribute("post", new Alcohol());  // null 절대 금지
        model.addAttribute("action", "/alcohol/write");
        model.addAttribute("back", "/alcohol/board");
        model.addAttribute("titleText", "자유게시판 글쓰기");
        return "alcohol/write";
    }

    // ===== 자유게시판 글 저장 =====
    @PostMapping("/write")
    public String writeForm(@RequestParam("title") String title,
                            @RequestParam("content") String content,
                            @RequestParam("author") String author) {
        alcoholService.write(title, content, author);
        return "redirect:/alcohol/board";
    }

    // ===== 자유게시판 글 수정 =====
    @GetMapping("/board/{id}/edit")
    public String editBoard(@PathVariable("id") Long id, Model model) {

        var post = alcoholService.getOne(id);  // 기존 글 불러오기

        model.addAttribute("post", post);   // write.html에서 바로 사용됨
        model.addAttribute("action", "/alcohol/board/" + id + "/edit");
        model.addAttribute("back", "/alcohol/board");
        model.addAttribute("titleText", "자유게시판 글 수정");
        return "alcohol/write";
    }

    // ===== 자유게시판 글 수정 처리 =====
    @PostMapping("/board/{id}/edit")
    public String editBoardForm(@PathVariable("id") Long id,
                                @RequestParam("title") String title,
                                @RequestParam("content") String content) {
        alcoholService.update(id, title, content);

        return "redirect:/alcohol/board/" + id;
    }

    // ===== 자유게시판 글 삭제 =====
    @DeleteMapping("/board/{id}")
    public String deleteBoard(@PathVariable("id") Long id) {
        alcoholService.delete(id);
        return "redirect:/alcohol/board";
    }

    // ===== 시음 후기 목록 =====
    @GetMapping("/review")
    public String review(Model model) {
        model.addAttribute("posts", reviewService.findAllDesc());
        return "alcohol/review";
    }


    // ===== 시음 후기 상세 =====
    @GetMapping("/review/{id}")
    public String reviewDetail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("post", reviewService.getOne(id));
        return "alcohol/review-detail";
    }

    // ===== 시음 후기 글 작성 =====
    @GetMapping("/review/write")
    public String reviewWrite(Model model) {
        model.addAttribute("post", new Review());  // null 금지
        model.addAttribute("action", "/alcohol/review/write");
        model.addAttribute("back", "/alcohol/review");
        model.addAttribute("titleText", "시음 후기 글쓰기");
        return "alcohol/write";
    }

    // ===== 시음 후기 글 저장 =====
    @PostMapping("/review/write")
    public String reviewWriteForm(@RequestParam("title") String title,
                                  @RequestParam("content") String content,
                                  @RequestParam("author") String author) {
        reviewService.write(title, content, author);
        return "redirect:/alcohol/review";
    }

    // ===== 시음 후기 글 수정 =====
    @GetMapping("/review/{id}/edit")
    public String editReview(@PathVariable("id") Long id, Model model) {

        var post = alcoholService.getOne(id);  // 기존 글 불러오기

        model.addAttribute("post", post);   // write.html에서 바로 사용됨
        model.addAttribute("action", "/alcohol/review/" + id + "/edit");
        model.addAttribute("back", "/alcohol/review");
        model.addAttribute("titleText", "시음 후기 글 수정");
        return "alcohol/write";
    }

    // ===== 시음 후기 글 수정 처리 =====
    @PostMapping("/review/{id}/edit")
    public String editReviewForm(@PathVariable("id") Long id,
                                 @RequestParam("title") String title,
                                 @RequestParam("content") String content) {
        alcoholService.update(id, title, content);

        return "redirect:/alcohol/review/" + id;
    }

    // ===== 시음 후기 글 삭제 =====
    @DeleteMapping("/review/{id}")
    public String deleteReview(@PathVariable("id") Long id) {
        reviewService.delete(id);
        return "redirect:/alcohol/review";
    }

    // ===== 도자기 굿즈 =====
    @GetMapping("/goods")
    public String goods(Model model) {
        model.addAttribute("titleText", "도자기 굿즈");
        return "alcohol/goods";
    }
}
