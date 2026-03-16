package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/login")
    public String getLogin() {
        return "/login";
    }

    @GetMapping("/logout")
    public String getLogout() {
        return "/logout";
    }
}
