package com.example.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class) // 엔티티의 생명주기 이벤트를 AuditingEntityListener가 가로채서 처리
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 256)
    private String title;
    @Column(length = 4096)
    private String description;
    @CreatedDate // 엔티티가 처음 저장될 때의 시간 자동 기록
    private LocalDateTime created;
    @LastModifiedDate // 엔티티가 수정될 때마다 마지막 수정 시간 자동 기록(INSERT 시에도 값 세팅됨)
    private LocalDateTime updated;
    @ManyToOne // 관계 매핑: 여러 개의 게시글이 하나의 회원에 매핑
    @JoinColumn(name = "member_id") // 생략시 필드명 + "_" + 참조 엔티티 PK 컬럼명, 명시적 작성 권장
    private Member member;
}
