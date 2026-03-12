package com.example.demo.service;

import com.example.demo.dto.MemberRequest;
import com.example.demo.dto.MemberResponse;
import com.example.demo.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberServiceTests {
    @Autowired
    private MemberService memberService; // 테스트 대상

    @Autowired
    private MemberRepository memberRepository; // 테스트 후 리포지터리를 사용해 검증하기 위해

    @AfterEach
    public void doAfterEach() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 추가 및 조회")
    public void testMembers() {
        // '윤서준' 회원을 추가하고 아이디가 자동으로 생성되었는지 검증
        MemberRequest memberRequest = MemberRequest.builder().name("윤서준").age(10).build();
        MemberResponse memberResponse = memberService.create(memberRequest);
        assertThat(memberResponse.getId()).isNotNull();

        // '윤광철' 회원을 추가하고 아이디가 자동으로 생성되었는지 검증
        memberRequest = MemberRequest.builder().name("윤광철").age(43).build();
        memberResponse = memberService.create(memberRequest);
        assertThat(memberResponse.getId()).isNotNull();

        // 회원을 모두 조회하여 두 명이 조회되는지 검증
        List<MemberResponse> results = memberService.findAll();
        assertThat(results.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("트랜잭션 커밋 테스트")
    public void testTransactionalCommit() {
        // 모두 네명의 사용자를 추가하며 데이터에 오류가 없기 때문에
        // 트랜잭션에서 4개의 입력 모두 커밋되어야 함
        List<MemberRequest> memberRequests = List.of(
                MemberRequest.builder().name("윤서준").email("SeojunYoon@goodee.co.kr").age(10).build(),
                MemberRequest.builder().name("윤광철").email("KwangcheolYoon@goodee.co.kr").age(43).build(),
                MemberRequest.builder().name("김도윤").email("DoyunKim@goodee.co.kr").age(11).build(),
                MemberRequest.builder().name("공미영").email("MiyeongKong@goodee.co.kr").age(28).build()
        );

        try {
            memberService.createMembers(memberRequests);
        } catch (Exception ignored) {
        }

        assertThat(memberRepository.count()).isEqualTo(4);
    }

    @Test
    @DisplayName("트랜잭션 롤백 테스트")
    public void testTransactionalRollback() {
        // 모두 네명의 사용자를 추가하지만 중복된 이메일이 있기 때문에
        // 트랜잭션에서 모두 롤백되어야 함
        List<MemberRequest> memberRequests = List.of(
                MemberRequest.builder().name("윤서준").email("SeojunYoon@goodee.co.kr").age(10).build(),
                MemberRequest.builder().name("윤광철").email("KwangcheolYoon@goodee.co.kr").age(43).build(),
                MemberRequest.builder().name("김도윤").email("SeojunYoon@goodee.co.kr").age(11).build(),
                MemberRequest.builder().name("공미영").email("MiyeongKong@goodee.co.kr").age(28).build()
        );

        try {
            memberService.createMembers(memberRequests);
        } catch (Exception ignored) {
        }

        assertThat(memberRepository.count()).isEqualTo(0);
    }

}
