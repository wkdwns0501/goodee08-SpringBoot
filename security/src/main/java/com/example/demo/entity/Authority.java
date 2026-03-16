package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String authority;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
