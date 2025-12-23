package com.sky.alcohol.web;

import com.sky.alcohol.app.AlcoholInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/alcoholInfo")
public class AlcoholInfoController {
    private final AlcoholInfoService alcoholInfoService;
}

