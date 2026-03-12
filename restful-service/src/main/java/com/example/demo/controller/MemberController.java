package com.example.demo.controller;

import com.example.demo.dto.ArticleRequest;
import com.example.demo.dto.ArticleResponse;
import com.example.demo.dto.MemberRequest;
import com.example.demo.dto.MemberResponse;
import com.example.demo.service.ArticleService;
import com.example.demo.service.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;
    private final ArticleService articleService;

    // @ResponseStatus: 요청 JSON body를 MemberRequest로 변환
    // 생성 성공 시 201 Created를 반환(200 OK보다 새 리소스가 생성됨을 더 정확히 나타냄)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping            // @RequestBody: 요청 body의 JSON을 MemberRequest 객체로 변환
    public List<MemberResponse> postMembers(@RequestBody List<MemberRequest> memberRequests) {
        return memberService.createMembers(memberRequests);
    }

    // 전체 조회
    @GetMapping
    public List<MemberResponse> getAll() {
        return memberService.findAll();
    }

    // 단일 조회
    @GetMapping("/{id}")
    public MemberResponse get(@PathVariable("id") Long id) {
        return memberService.findById(id);
    }

    // 전체 수정
    @PutMapping("/{id}")
    public MemberResponse put(@PathVariable("id") Long id, @RequestBody MemberRequest memberRequest) {
        return memberService.update(id, memberRequest);
    }

    // 부분 수정
    @PatchMapping("/{id}")
    public MemberResponse patch(@PathVariable("id") Long id, @RequestBody MemberRequest memberRequest) {
        return memberService.patch(id, memberRequest);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        memberService.deleteById(id);
    }

    // 게시글 등록
    @PostMapping("/{id}/articles")
    @ResponseStatus(HttpStatus.CREATED) // 201 Created
    public ArticleResponse postArticle(@PathVariable("id") Long memberId, @RequestBody ArticleRequest articleRequest) {
        return articleService.create(memberId, articleRequest);
    }

    // 특정 회원이 작성한 게시글 목록 조회
    // 1. 리다이렉트 예시
//    @GetMapping("/{id}/articles")
//    public void getArticles(@PathVariable("id") Long memberId, HttpServletResponse response) throws IOException {
//        response.sendRedirect("/api/articles?memberId=" + memberId);
//    }

    // 2. 포워딩 예시
    @GetMapping("/{id}/articles")
    public void getArticles(@PathVariable("id") Long memberId,
                           HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/api/articles?memberId=" + memberId)
                .forward(request, response);
    }
}
