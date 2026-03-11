package com.example.jpa.dto;

// Native SQL 조회 결과를 인터페이스 프로젝션으로 받기 위한 타입
public interface MemberStatsNative {

    String getName();
    String getEmail();
    Long getCount();

}
