package com.example.demo.repository;

import com.example.demo.entity.Authority;
import com.example.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    // 회원 엔티티를 작성할 때 회원 권한과의 관계 매핑을 하지 않았으므로,
    // 회원을 조회할 때 자동으로 권한이 함께 조회되지 않음
    // 회원 정보를 사용해 해당 회원에게 부여된 권한 목록을 조회하기 위한 쿼리 메소드
    List<Authority> findByMember(Member member);
}
