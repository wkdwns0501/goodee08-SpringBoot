package com.example.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// 모델(도메인) 객체: 데이터를 관리할 목적의 객체(예: DTO, VO, Entity)
@Data
//@Table(name = "vip_member") // DB 테이블명이 다르면 반드시 필요
@Table(indexes = { // 인덱스 설정
        @Index(name = "idx_name_age", columnList = "name, age"), // 복합 인덱스(컬럼 순서 매우 중요!)
        @Index(name = "idx_email", columnList = "email"),
})
@Entity // 객체를 DB의 테이블과 연결(연동)하기 위해 사용(엔티티 객체로 정의)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
//    @Id // 아이디(PK)로 사용하는 속성에 사용(조회, 수정, 삭제시 기준이 됨)
////    @GeneratedValue // ID 생성 전략 선언, 애플리케이션이 아니라 DB에서 자동으로 생성된 값이 할당됨을 알려줌
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB의 IDENTITY 컬럼 기능을 사용하여 기본 키를 생성
//    private Long id;
//    @Column(name = "display_name") // DB 컬럼명이 다르면 반드시 필요(DB에 name으로 컬럼 생성)
//    private String name;
//    @Column(name = "primary_contact")
//    private String email;
//    private Integer age;
    // JPA는 필드의 타입을 보통 wrapper 타입으로 선언

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 128, nullable = false) // 이런 제약조건들은 properties에 ddl-auto이 none이면 의미없는 코드
    private String name;                    // (보통의 DB들은 기본값이 none이기 때문에 설정을 해줘야만 작동
    @Column(length = 256, nullable = false, unique = true)
    private String email;
    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 10")
    private Integer age;
    @Transient // 해당 속성이 DB 테이블과 매핑되지 않도록 설정
    private String address; // 순수하게 애플리케이션 내부에서만 사용하는 필드(값)

    @OneToMany(mappedBy = "member") // Article 엔티티에 member라는 @ManyToOne 필드
    // 이 관계의 실제 주인은 Article.member임을 알려줌(FK를 관리하는 쪽이 아니라는 뜻)
    private List<Article> articles;

}

