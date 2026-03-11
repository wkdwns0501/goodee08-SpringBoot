package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class Pi {

    @PrintExecutionTime // 어노테이션만 붙이면 메서드 실행 시간을 간단히 구할 수 있음
    double calculate(int points) {
        int circle = 0;
        for (long i = 0; i < points; i++) {
            double x = Math.random() * 2 - 1;
            double y = Math.random() * 2 - 1;
            if (x * x + y * y <= 1) {
                circle++;
            }
        }

        return 4.0 * circle / points;
    }
}
