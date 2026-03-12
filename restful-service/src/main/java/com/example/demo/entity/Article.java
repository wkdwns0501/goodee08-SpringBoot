package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @CreatedDate
    private LocalDateTime created;
    @LastModifiedDate
    private LocalDateTime updated;
    // (중요) JPA에서 지원하는 엔티티 연관 관계 매핑을 사용해 게시글을 조회할 때 회원 정보도 함ㄲ조회
    @ManyToOne // 두 객체 간 관계를 설정: 여러 개의 게시글이 한 명의 회원에 매핑될 수 있음(N:1)
    @JoinColumn(name = "member_id") // 생략해도 되지만 명시적으로 작성해주는 것이 좋음
    private Member member; // 회원 아이디X, 회원 객체O
}
