package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ArticleResponse {
    // 일반적으로 게시판에서 게시글을 보여 줄 때는 작성자 정보도 함께
    // 생성된 게시글의 아이디뿐만 아니라 작성한 회원의 아이디와 이름, 이메일도 함께 포함해 전달하면
    // 클라이언트가 필요에 따라 사용할 수 있음
    // 특히 게시글 생성 시간과 수정 시간은 JPA가 자동으로 관리하도록 설정
    private Long id;
    private Long memberId;
    private String name;
    private String email;
    private String title;
    private String description;
    private LocalDateTime created;
    private LocalDateTime updated;
}