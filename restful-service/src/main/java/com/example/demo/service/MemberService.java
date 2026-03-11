package com.example.demo.service;

import com.example.demo.dto.MemberRequest;
import com.example.demo.dto.MemberResponse;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    private MemberResponse mapToMemberResponse(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .age(member.getAge())
                .build();
    }

    // 회원 정보를 생성 (사용자의 요청을 받아서 회원 객체로 생성 후 응답으로 반환)
    public MemberResponse create(MemberRequest memberRequest) {
        Member member = Member.builder()
                .name(memberRequest.getName())
                .email(memberRequest.getEmail())
                .age(memberRequest.getAge())
                .enabled(true) // default enabled is true
                .build();
        memberRepository.save(member);

        return mapToMemberResponse(member);
    }

    // 회원 목록 전체 조회
    public List<MemberResponse> findAll() {
        // 방법 1
        // MemberResponse DTO 객체로 변환하고, 이를 다시 리스트에 추가
        return memberRepository.findAll() // 가져온 회원 리스트에
                .stream() // 회원 엔티티 객체를 하나씩 스트림으로 보내고
                .map(member -> mapToMemberResponse(member)) // 새로운 DTO 객체로 매핑한 후
                .toList(); // 다시 리스트로 만듦

        // 방법 2
//        List<Member> members = memberRepository.findAll();
//        ArrayList<MemberResponse> memberResponses = new ArrayList<>();
//        for (Member member : members) {
//            MemberResponse memberResponse = mapToMemberResponse(member);
//            memberResponses.add(memberResponse);
//        }
//        return memberResponses;
    }

    //

}