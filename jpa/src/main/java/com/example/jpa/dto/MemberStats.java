package com.example.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// JPQL의 new DTO(...) 조회 결과를 담는 DTO
@Data
@NoArgsConstructor
@AllArgsConstructor // 쿼리 결과를 담으려면 필수
public class MemberStats {
    private String name;
    private String email;
    private Long count;
}
