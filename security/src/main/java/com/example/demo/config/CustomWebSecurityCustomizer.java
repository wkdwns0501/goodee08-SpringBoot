package com.example.demo.config;

import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

// WebSecurityCustomizer 사용 예시용 클래스
//@Component
public class CustomWebSecurityCustomizer implements WebSecurityCustomizer {
    @Override
    // 빈으로 등록되면 지정 경로를 시큐리티 검사에서 제외
    public void customize(WebSecurity web) {
        web.ignoring().requestMatchers("");
    }
}
