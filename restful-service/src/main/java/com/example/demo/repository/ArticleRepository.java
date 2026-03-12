package com.example.demo.repository;

import com.example.demo.entity.Article;
import com.example.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    // 특정 회원이 작성한 게시글만 조회할 수 있도록 쿼리 메소드를 추가로 정의
    List<Article> findByMember(Member member);
}