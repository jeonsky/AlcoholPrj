package com.sky.alcohol.web;

import com.sky.alcohol.app.AlcoholInfoService;
import com.sky.alcohol.domain.AlcoholInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/alcohol")
public class AlcoholInfoController {

    private final AlcoholInfoService alcoholInfoService;

    // 주류 도감 목록
    @GetMapping("/alcoholInfo")
    public String list(Model model) {
        model.addAttribute("infos", alcoholInfoService.findAllDesc());
        return "alcohol/alcoholInfo";
    }

    // 주류 도감 상세
    @GetMapping("/alcoholInfo/{id:\\d+}")
    public String detail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("info", alcoholInfoService.getOne(id));
        return "alcohol/alcoholInfo-detail";
    }

    // 주류 도감 작성 폼
    @GetMapping("/alcoholInfo/write")
    public String writeForm(Model model) {
        model.addAttribute("info", new AlcoholInfo());
        model.addAttribute("titleText", "주류 도감 등록");
        return "alcohol/alcoholInfo-write";
    }

    // 주류 도감 저장
    @PostMapping("/alcoholInfo/write")
    public String save(@ModelAttribute AlcoholInfo info) {
        alcoholInfoService.save(info);
        return "redirect:/alcohol/alcoholInfo";
    }
}
