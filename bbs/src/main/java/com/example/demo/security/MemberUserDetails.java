package com.example.demo.security;

import com.example.demo.entity.Authority;
import com.example.demo.entity.Member;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Data
public class MemberUserDetails implements UserDetails {

    // User Details 디폴트 구현 getUsername(), getPassword(), getAuthorities()
    private String username;
    private String password;
    private List<SimpleGrantedAuthority> authorities;

    // Extras: 그 밖에 애플리케이션에서 사용할 회원 정보가 있을 경우 추가로 필드를 만들어서 저장하면
    // 스프링 시큐리티를 통해 추가 확장된 정보를 사용할 수 있음
    private String displayName;
    private Long memberId;

    public MemberUserDetails(Member member, List<Authority> authorities) {
        this.username = member.getEmail(); // 동명이인이 있을 수 있으므로 고유한 값인 이메일을 username으로 사용
        this.displayName = member.getName(); // 그 대신 displayName으로 회원의 이름을 저장
        this.password = member.getPassword();
        this.memberId = member.getId(); // 나중에 게시글을 작성하거나 수정할 때는 멤버 아이디가 필요하므로 memberId 필드를 추가
        this.authorities = authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .toList();
    }

}
