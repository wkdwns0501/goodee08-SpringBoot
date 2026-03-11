package com.example.demo;

// 원주율 계산기 - 원주율을 계산하는 메소드 수행 시간 측정
public class PiCalculator {

    public static void main(String[] args) {
        PiCalculator pi = new PiCalculator();
        System.out.println(pi.calculate(100000000));
    }

    double calculate(int points) {
        long start = System.currentTimeMillis();

        int circle = 0;
        for (long i = 0; i < points; i++) {
            double x = Math.random() * 2 - 1;
            double y = Math.random() * 2 - 1;
            if (x * x + y * y <= 1) {
                circle++;
            }
        }

        long executionTime = System.currentTimeMillis() - start;
        System.out.println("executed in " + executionTime + "ms.");
        // 수행 시간을 알고 싶은 메소드마다 위와 같이 구현한다면 코드가 중복되고 유지보수가 어려움

        return 4.0 * circle / points;
    }

}
