package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

// 이 클래스가 매핑될 데이터베이스 테이블을 지정
// 기본적으로 클래스명 -> 테이블명 규칙 사용
// snake_case로 변환
@Table
//@Table("members") // 테이블명이 다를 경우 명시적으로 지정
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    // 해당 컬럼이 PK(Primary Key)
    // Spring Data JDBC에서 엔티티의 식별자
    @Id // 반드시 필요(없으면 PK 식별 불가, INSERT / UPDATE 판단 불가)
    private Long id;
    private String name;
//    @Column("user_email") // 컬럼명이 다를 경우 명시적으로 지정
    private String email;
    private Integer age;
    // 컬럼과 1:1 매핑
    // 기본 규칙: 필드(속성)명 = 컬럼명
    // snake_case로 변환

}
