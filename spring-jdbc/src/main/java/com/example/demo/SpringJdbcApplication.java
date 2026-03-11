package com.example.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class SpringJdbcApplication implements ApplicationRunner {

    private final MemberRepository memberRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // create
//        Member member = Member.builder()
//                            .name("장훈")
//                            .email("HoonJang@goodee.co.kr")
//                            .age(10)
//                            .build();
//        memberRepository.save(member);
//        log.info("{}", member);

        // update
//        member.setAge(11);
//        memberRepository.save(member);
//        log.info("{}", member);

        // find all members
        Iterable<Member> members = memberRepository.findAll();
        log.info("{}", members);

        // find member by id
        Optional<Member> member = memberRepository.findById(1L);
        log.info("{}", member);

    }
}
