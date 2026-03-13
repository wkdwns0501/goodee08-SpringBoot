package com.example.demo.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
// 클라이언트가 요청한 URI 정보와 이를 처리하는데 걸린 시간을 출려하는 로그 필터
//@WebFilter(urlPatterns = "/api/*") // 모든 /api/* 요청에 대해 로그 필터를 거침
public class LogFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 요청이 들어올 때 해야 할 작업
        long start = System.currentTimeMillis();
        log.info("LogFilter.doFilter() 시작 - {}", request.getRequestURI());

        // 다음 필터 처리
        chain.doFilter(request, response);

        // 요청을 처리하고 나갈 때 해야 할 작업
        long timeElapsed = System.currentTimeMillis() - start;
        log.info("LogFilter.doFilter() 종료 - {} status {} in {} ms",
                request.getRequestURI(), response.getStatus(), timeElapsed);
    }
}
