package com.example.demo.controller;

import com.example.demo.dto.ArticleDto;
import com.example.demo.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;

//    @GetMapping("/list")
//    public String getArticleList(Model model) {
//        List<ArticleDto> articles = articleService.findAll();
//        model.addAttribute("articles", articles);
//        return "/article/article-list";
//    }

    // 게시글 목록(페이지네이션 적용)
    @GetMapping("/list")
    public String getArticleList( // 에: http://localhost:8080/article/list?page=0&size=10&sort=id,desc
            // @PageableDefault: URL에 페이지 정보가 없을 때 디폴트 페이지 객체를 생성할 수 있도록 해줌
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            Model model) {
        Page<ArticleDto> articlesByPage = articleService.findAll(pageable);
        model.addAttribute("page", articlesByPage);

        return "/article/article-list";
    }

}
