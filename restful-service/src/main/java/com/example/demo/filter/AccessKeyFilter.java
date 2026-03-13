package com.example.demo.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
//@WebFilter(urlPatterns = "/api/*")
public class AccessKeyFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("AccessKeyFilter.doFilterInternal() 시작");

        // 전달된 request에서 Authorization 헤더를 구한 후
        // Bearer를 제거한 엑세스 키가 정상값인지 검증한 다음,
        // 정상이면 다음 단계 진행
        // 그렇지 않은 경우 오류 401을 반환
        String authorization = request.getHeader("Authorization"); // 인증 정보를 담는 HTTP 헤더
        if (authorization != null && authorization.startsWith("Bearer")) { // 이 값이 인증 토큰이라면
            String token = authorization.replace("Bearer", "").trim();

            if ("goodee-access-key".equals(token)) {
                filterChain.doFilter(request, response);
                log.info("AccessKeyFilter.doFilterInternal() returns");
                return;
            }

        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        log.info("AccessKeyFilter.doFilterInternal() returns 401 UNAUTHORIZED");
    }

}
