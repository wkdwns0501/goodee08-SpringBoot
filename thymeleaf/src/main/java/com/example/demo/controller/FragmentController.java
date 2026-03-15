package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FragmentController {

    @GetMapping("/fragment/common")
    public String getFragment() {
        return "fragment/fragment-common";
    }

    @GetMapping("/fragment/layout")
    public String getFragmentLayout() {
        return "fragment/fragment-layout";
    }

}
