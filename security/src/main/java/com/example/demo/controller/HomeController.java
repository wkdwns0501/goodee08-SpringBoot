package com.example.demo.controller;

import com.example.demo.security.MemberUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class HomeController {
    @GetMapping("/")
    public String getRoot() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String getHome(@AuthenticationPrincipal MemberUserDetails userDetails) {
        log.info("{}", userDetails); // 로그인한 사용자 정보
        return "/home";
    }
}
