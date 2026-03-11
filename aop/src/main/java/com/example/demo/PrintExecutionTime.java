package com.example.demo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 어노테이션은 클래스, 메소드, 프로퍼티에 적용 가능(예: TYPE, METHOD, FIELD)
@Target(ElementType.METHOD) // 이 어노테이션을 사용할 수 있는 대상이 메소드라는 것을 의미
// 어노테이션은 컴파일 또는 런타임에 적용 가능(예: SOURCE, CLASS, RUNTIME)
@Retention(RetentionPolicy.RUNTIME) // 런타임, 즉 컴파일 + 실행 단계까지 적용하는 어노테이션임을 설정
public @interface PrintExecutionTime {

}
