package com.example.demo.security;

import com.example.demo.entity.Authority;
import com.example.demo.entity.Member;
import com.example.demo.repository.AuthorityRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    // 로그인 시 이메일로 회원과 권한을 조회해 UserDetails로 만든다.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 사용자가 입력한 아이디(username)를 사용해 멤버 리포지터리에서 멤버 객체를 조회하고
        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        // 다시 이를 사용해 권한 리포지터리에서 해당 멤버의 권한을 조회한 후
        List<Authority> authorities = authorityRepository.findByMember(member);
        // 우리가 확장 정의한 UserDetails 객체로 만들어 반환
        return new MemberUserDetails(member, authorities);
    }
}
