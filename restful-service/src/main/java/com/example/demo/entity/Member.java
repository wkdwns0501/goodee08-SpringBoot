package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    private Integer age;
    private String password;
    private Boolean enabled;

    // mappedBy: FK는 Article 엔티티의 member 속성이 관리한다는 의미(member 속성이 연관관계의 주인)
    // cascade: 부모(Member)의 영속성 상태 변화가 자식(Article)에게 전파
    // CascadeType.REMOVE: 부모가 삭제될 때 자식도 함께 삭제, 회원을 삭제할 때 해당 회원이 참조하는 게시글을 자동으로 삭제
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Article> articles;
}