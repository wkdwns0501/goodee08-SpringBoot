package com.example.demo.controller;

import com.example.demo.entity.Member;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiController {
    private List<Member> members = List.of(
            Member.builder().id(1L).name("윤서준").email("SeojunYoon@goodee.co.kr").build(),
            Member.builder().id(2L).name("윤광철").email("KwangcheolYoon@goodee.co.kr").build(),
            Member.builder().id(3L).name("공미영").email("MiyeongKong@goodee.co.kr").build(),
            Member.builder().id(4L).name("김도윤").email("DoyunKim@goodee.co.kr").build()
    );

    @GetMapping("/api/members")
    public List<Member> getMembers() {
        return members;
    }
}
