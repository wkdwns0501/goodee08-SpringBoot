package com.example.demo.repository;

import com.example.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // 스프링 시큐리티를 통해 로그인을 수행할 때 로그인 아이디로 이메일을 사용할 것이므로,
    // 이메일로 회원 정보를 조회하기 위한 쿼리 메소드들 추가로 정의
    Optional<Member> findByEmail(String email);
}
