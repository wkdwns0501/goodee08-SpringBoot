package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BasicController {

    @GetMapping("/book")
    public String getBook(Model model) {
        model.addAttribute("title", "스프링 부트 입문");
        model.addAttribute("description", "실무로 이어지는 스프링 부트");

        return "basic/book"; // 템플릿 이름 반환
    }
}
