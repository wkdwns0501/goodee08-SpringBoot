package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ArticleDto {
    private Long id;
    private Long memberId;
    // 여기에는 게시글의 정보뿐만 아니라 @ManyToOne 관계 매핑으로 가져온 멤버 엔티티의 이름과 이메일을 포함
    private String name;
    private String email;
    private String title;
    private String description;
    private LocalDateTime created;
    private LocalDateTime updated;
}
