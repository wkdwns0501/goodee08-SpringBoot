package com.example.demo;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// CrudRepository<>는 제네릭 타입으로 테이블과 연동할 객체의 타입과 그 객체의 아이디 타입을 매개변수로 제공
public interface MemberRepository extends CrudRepository<Member, Long> {
    // 기본적인 CRUD 메소드(save, findById, findAll, deleteById 등) 자동 제공

    // 쿼리 메소드 기능을 제공
    // 이름 또는 이메일로 검색하는 메소드(비교할 값은 매개변수로 전달)
    List<Member> findByName(String name);
    List<Member> findByEmail(String email);
    // 단순 비교가 아닌 포함 여부를 검색 조건을 정의할 수도 있음
    List<Member> findByNameContaining(String name); // LIKE 검색(%값%)

    // 2개 이상의 속성을 사용해 검색 조건을 표현하려면 And 또는 Or로 연결
    List<Member> findByNameAndEmail(String name, String email);
    List<Member> findByNameOrEmail(String name, String email);

    // 숫자로 된 컬럼에 대해 크기를 비교해 검색하는 메소드
    List<Member> findByAgeGreaterThan(Integer age); // >
    List<Member> findByAgeLessThan(Integer age); // <
    List<Member> findByAgeBetween(Integer min, Integer max); // <= <=

    // 복잡한 쿼리는 직접 SQL을 작성할 수 있음
    @Query("SELECT * FROM member WHERE age >= 13 AND age <= 19")
    List<Member> findTeenAge();

    // SQL 문에 콜론(:)을 붙여 쿼리 메소드의 매개변수를 SQL 문으로 전달할 수 있음
    @Query("SELECT * FROM member WHERE age >= :min AND age <= :max")
    List<Member> findByAgeRange(Integer min, Integer max);
}
