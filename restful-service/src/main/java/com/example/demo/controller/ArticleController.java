package com.example.demo.controller;

import com.example.demo.dto.ArticleRequest;
import com.example.demo.dto.ArticleResponse;
import com.example.demo.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class ArticleController {
    private final ArticleService articleService;

    // memberId가 null이면 전체 게시글 조회 후 반환
    // memberId가 null이 아니면 해당 회원의 게시글 조회 후 반환
    @GetMapping
    public List<ArticleResponse> getArticles(@RequestParam(name = "memberId", required = false) Long memberId) {
        // @RequestParam으로 선언된 매개변수가 전달되지 않을 때 요청이 잘못되었다는
        // 400 Bad Request 오류가 발생하는 것을 방지하기 위해 required 옵션을 사용
        if(memberId == null) return articleService.findAll();
        else return articleService.findByMemberId(memberId);
    }

    @GetMapping("/{id}")
    public ArticleResponse get(@PathVariable("id") Long id) {
        return articleService.findById(id);
    }

    @PutMapping("/{id}")
    public ArticleResponse put(@PathVariable("id") Long id, @RequestBody ArticleRequest articleRequest) {
        return articleService.update(id, articleRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        articleService.delete(id);
    }
}
