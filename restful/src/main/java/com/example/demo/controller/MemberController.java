package com.example.demo.controller;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberRepository memberRepository;

    // 생성
    @PostMapping
    public Member post(@RequestBody Member member) { // 클라이언트가 보낸 요청 본문으로부터 JSON
        return memberRepository.save(member);        // -> Member 객체로 만들어 파라미터로 전달
    }

    // 전체 조회
    @GetMapping
    public List<Member> getAll() {
        return memberRepository.findAll();
    }

    // 단일 조회
    @GetMapping("/{id}")
    public Member get(@PathVariable("id") Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    // 전체 수정
    @PutMapping("/{id}")
    public Member put(@PathVariable("id") Long id, @RequestBody Member member) {
        member.setId(id);
        return memberRepository.save(member);
    }

    // 부분 수정
    @PatchMapping("/{id}")
    public Member patch(@PathVariable("id") Long id, @RequestBody Member patch) {
        // 우선 아이디로 회원 정보를 조회하고 전달된 회원 객체(patch)의 프로퍼티 중
        // null이 아닌 것들만 값을 바꿔 데이터베이스에 저장하도록 구현
        memberRepository.findById(id).ifPresent(member -> {
            if (member != null) {
                if (patch.getName() != null) member.setName(patch.getName());
                if (patch.getEmail() != null) member.setEmail(patch.getEmail());
                if (patch.getAge() != null) member.setAge(patch.getAge());

                memberRepository.save(member);
            }
        });

        return null;
    }

    // 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        memberRepository.deleteById(id);
    }

}
