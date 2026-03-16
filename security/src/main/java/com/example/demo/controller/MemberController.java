package com.example.demo.controller;

import com.example.demo.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
public class MemberController {
    private List<Member> members = List.of(
            Member.builder().id(1L).name("윤서준").email("SeojunYoon@goodee.co.kr").build(),
            Member.builder().id(2L).name("윤광철").email("KwangcheolYoon@goodee.co.kr").build(),
            Member.builder().id(3L).name("공미영").email("MiyeongKong@goodee.co.kr").build(),
            Member.builder().id(4L).name("김도윤").email("DoyunKim@goodee.co.kr").build()
    );

    @GetMapping("/member/list")
    public String getMemberList(Model model) {
        model.addAttribute("members", members);

        return "/member-list";
    }
}
