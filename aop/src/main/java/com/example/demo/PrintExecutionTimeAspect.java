package com.example.demo;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect // 이 클래스가 AOP를 위한 구현체라는 것을 설정
public class PrintExecutionTimeAspect {

    @Around("@annotation(PrintExecutionTime)") // @PrintExecutionTime 어노테이션이 붙은 메소드에 적용
    public Object printExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed(); // @PrintExecutionTime이 붙은 타겟 메소드 실행
        long executionTime = System.currentTimeMillis() - start;

        System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");

        return proceed; // 메소드의 원래 반환값 반환
    }

    // 조인 포인트가 실행되기 전에 수행할 로직을 구현하는 어노테이션
    @Before("@annotation(PrintExecutionTime)")
    public void beforePrintExecutionTime(JoinPoint joinPoint) {
        System.out.println("메소드 실행 전에 수행할 로직");
    }

    // 조인 포인트가 실행된 후에 수행할 로직을 구현하는 어노테이션
    @After("@annotation(PrintExecutionTime)")
    public void afterPrintExecutionTime(JoinPoint joinPoint) {
        System.out.println("메소드 실행 후에 수행할 로직");
    }

    // 조인 포인트가 실행되고 정상적으로 반환된 후에 수행할 로직을 구현하는 어노테이션
    @AfterReturning(pointcut = "@annotation(PrintExecutionTime)", returning = "result")
    public void afterReturningPrintExecutionTime(JoinPoint joinPoint, Object result) {
        System.out.println("메소드가 정상적으로 반환된 후에 수행할 로직");
        System.out.println("반환값: " + result);
    }

    // 조인 포인트가 실행되고 예외가 발생한 후에 수행할 로직을 구현하는 어노테이션
    @AfterThrowing(pointcut = "@annotation(PrintExecutionTime)", throwing = "ex")
    public void afterThrowingPrintExecutionTime(JoinPoint joinPoint, Throwable ex) {
        System.out.println("메소드 실행 중 예외가 발생한 후에 수행할 로직");
        System.out.println("예외 메세지: " + ex.getMessage());
    }

}
