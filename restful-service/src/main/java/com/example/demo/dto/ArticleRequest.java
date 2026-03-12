package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArticleRequest {
    // 게시글의 생성과 수정을 요청할 때는 제목과 본문만 있으면 됨
    private String title;
    private String description;
}