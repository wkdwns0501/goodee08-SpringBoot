package com.example.demo.security;

import com.example.demo.entity.Authority;
import com.example.demo.entity.Member;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Data
// Member 엔티티를 시큐리티 로그인 사용자 정보로 감싼다.
public class MemberUserDetails implements UserDetails {

    // UserDetails 디폴트 구현 getAuthorities(), getPassword(), getUsername()
    private String username;
    private String password;
    private List<SimpleGrantedAuthority> authorities;

    // Extras: 그 밖에 애플리케이션에서 사용할 회원 정보가 있을 경우 추가로 필드를 만들어서 저장하면
    // 스프링 시큐리티를 통해 추가 확장된 정보를 사용할 수 있음
    private String displayName;
    private Long memberId;

    // 회원 정보와 권한 목록을 UserDetails 필드로 변환한다.
    public MemberUserDetails(Member member, List<Authority> authorities) {
        this.username = member.getEmail(); // 동명이인이 있을 수 있으므로 고유한 값인 이메일을 사용
        this.displayName = member.getName(); // 그 대신 displayName으로 회원의 이름을 저장
        this.password = member.getPassword();
        this.memberId = member.getId(); // 나중에 게시글을 작성하거나 수정할 때는 다른 멤버 아이디(PK)가 필요
        this.authorities = authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .toList();
        // SimpleGrantedAuthority
        // 스프링 시큐리티가 권한을 표현하기 위해 사용하는 기본 구현체
        // 권한을 문자열 형태로 저장하는 단순한 권한 객체
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of();
//    }
//
//    @Override
//    public String getPassword() {
//        return "";
//    }
//
//    @Override
//    public String getUsername() {
//        return "";
//    }
}
