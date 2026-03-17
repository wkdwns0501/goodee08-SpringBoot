package com.example.demo.service;

import com.example.demo.dto.MemberDto;
import com.example.demo.dto.MemberForm;
import com.example.demo.entity.Member;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원 아이디로 검색하는 기능
    public MemberDto findById(Long id) {
        return memberRepository.findById(id)
                .map(this::mapToMemberDto) // Optional<Member> -> Optional<MemberDto>
                .orElseThrow(); // 결과가 0개이면 NoSuchElementException 발생
                                // 커스텀 예외를 만들어서 발생시키는 것을 권장
    }

    // 회원 가입 기능
    // 패스워드를 그대로 저장하는 것이 아니라 스프링 빈으로 등록된 PasswordEncoder를 사용해 인코딩한 다음에 저장
    public MemberDto create(MemberForm memberForm) {
        Member member = Member.builder()
                .name(memberForm.getName())
                .password(passwordEncoder.encode(memberForm.getPassword()))
                .email(memberForm.getEmail())
                .build();
        memberRepository.save(member);
        return mapToMemberDto(member);
    }

    // 이메일로 회원을 검색하는 기능
    // 이미 가입된 멤버와 비교해 중복되는 이메일이 있는지를 확인해야 함
    public Optional<MemberDto> findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .map(this::mapToMemberDto);
    }

    // 입력받은 기존 패스워드가 DB에 저장된 패스워드와 일치하는지 확인
    public boolean checkPassword(Long id, String password) {
        Member member = memberRepository.findById(id).orElseThrow();

        // 단방향 인코더이므로 원래 패스워드를 알 수 없고, 오직 matches()를 통해 확인만 할 수 있음
        return passwordEncoder.matches(password, member.getPassword());
    }

    // 패스워드 변경 저장을 위한 기능
    public void updatePassword(Long id, String password) {
        Member member = memberRepository.findById(id).orElseThrow();
        member.setPassword(passwordEncoder.encode(password));
        memberRepository.save(member); // 트랜젝션이 있으면 안적어도 변경 사항 감지해서 save
    }

    // 회원 엔티티 객체를 DTO 객체로 변환하는 기능
    public MemberDto mapToMemberDto(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }
}
