package com.example.demo.controller;

import com.example.demo.dto.MemberRequest;
import com.example.demo.dto.MemberResponse;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    // @ResponseStatus: 요청 JSON body를 MemberRequest로 변환
    // 생성 성공 시 201 Created를 반환(200 OK보다 새 리소스가 생성됨을 더 정확히 나타냄)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public MemberResponse post(@RequestBody MemberRequest memberRequest) {
        return memberService.create(memberRequest);
    }

    // 전체 조회
    @GetMapping
    public List<MemberResponse> getAll() {
        return memberService.findAll();
    }
}
