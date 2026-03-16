package com.example.demo.repository;

import com.example.demo.entity.Authority;
import com.example.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    // 멤버를 사용해 해당 멤버의 권한을 조회
    List<Authority> findByMember(Member member);
}
