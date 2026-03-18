package com.example.demo.service;

import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.ArticleForm;
import com.example.demo.entity.Article;
import com.example.demo.entity.Member;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    // 게시글 목록
    public List<ArticleDto> findAll() {
        return articleRepository.findAll()
                .stream() // List 안에 stream
                .map(this::mapToArticleDto)
                .toList();
    }

    // 게시글 목록 + 페이징
    public Page<ArticleDto> findAll(Pageable pageable) {
        return articleRepository.findAll(pageable)
                .map(this::mapToArticleDto);
        // 이때 반환되는 Page 객체에는 게시글 목록뿐만 아니라
        // 페이지네이션을 구성하는 데 필요한 모든 정보가 들어 있음
    }

    // 게시글 상세 보기
    public ArticleDto findById(Long id) {
        return articleRepository.findById(id)
                .map(this::mapToArticleDto)
                .orElseThrow();
    }

    // 게시글 생성 (확장을 대비해 객체를 반환 또는 void)
    public ArticleDto create(Long memberId, ArticleForm articleForm) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Article article = Article.builder()
                .title(articleForm.getTitle())
                .description(articleForm.getDescription())
                .member(member)
                .build();

        articleRepository.save(article);
        // 일반적으로 자동 할당된 ID를 포함한 DTO 객체를 반환하도록 구현
        // (확장을 대비하여 서비스는 보통 컨트롤러와 독립적으로 설계)
        return mapToArticleDto(article);
    }

    // 게시글 수정
    public ArticleDto update(Long memberId, ArticleForm articleForm) {
        // 기존에 작성된 게시글이 없다면 잘못된 요청이므로 예외를 발생시키고,
        // 존재하는 게시글이라면 제목과 본문의 내용을 게시글 DTO 객체에 들어있는 내용으로 교체해 리포지터리에 저장
        Article article = articleRepository.findById(articleForm.getId()).orElseThrow();

        // (보안 체크) 수정하려는 게시글이 로그인한 사용자에 의해 작성되었는지 확인
        if (!article.getMember().getId().equals(memberId)){
            throw new AccessDeniedException("권한 없음");
        }

        article.setTitle(articleForm.getTitle());
        article.setDescription(articleForm.getDescription());
        articleRepository.save(article);
        return mapToArticleDto(article);
    }

    // (보안 추가)
    public boolean isOwner(Long articleId, Long memberId) {
        if (articleId == null || memberId == null) {
            return false;
        }
        return articleRepository.findById(articleId)
                .map(article -> memberId.equals(article.getMember().getId()))
                .orElse(false);
    }

    // 게시글 삭제
    public void delete(Long memberId, Long articleId) {
        if (articleId == null) {
            throw new IllegalArgumentException("잘못된 게시글 요청입니다.");
        }

        Article article = articleRepository.findById(articleId).orElseThrow();

        if (!article.getMember().getId().equals(memberId)) {
            throw new AccessDeniedException("권한 없음");
        }

        articleRepository.delete(article);
    }

    // 게시글 엔티티 객체를 DTO 객체로 변환하는 기능
    public ArticleDto mapToArticleDto(Article article) {
        return ArticleDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .description(article.getDescription())
                .created(article.getCreated())
                .updated(article.getUpdated())
                .memberId(article.getMember().getId())
                .name(article.getMember().getName())
                .email(article.getMember().getEmail())
                .build();
    }
}
