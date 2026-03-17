package com.example.demo.repository;

import com.example.demo.entity.Article;
import com.example.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    // 게시글과 회원이 관계 매핑되어 있어
    // 회원이 작성한 게시글을 모두 삭제한 후에야 해당 회원을 삭제할 수 있으므로,
    // 회원을 매개변수로 해당 회원과 관계 매핑된 게시글을 모두 삭제하기 위한 메소드를 추가로 자것ㅇ
    void deleteAllByMember(Member member);
}
