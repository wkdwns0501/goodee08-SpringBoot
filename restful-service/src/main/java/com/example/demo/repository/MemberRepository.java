package com.example.demo.repository;

import com.example.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // 테스트 코드 작성을 위한 사전 준비
    // JPA Query Methods
    List<Member> findByName(String name);
    List<Member> findByNameAndEmail(String name, String email);
    List<Member> findByNameOrEmail(String name, String email);
    List<Member> findByNameContaining(String name); // where name like %?%
    List<Member> findByNameLike(String name); // where name like ?
    List<Member> findByAgeGreaterThan(Integer age);
    List<Member> findByAgeGreaterThanEqual(Integer age);
    List<Member> findByAgeLessThan(Integer age);
    List<Member> findByAgeLessThanEqual(Integer age);

    // JPA Query Methods for ORDER BY
    List<Member> findAllByOrderByNameAsc();

    // JPA Query Methods for WHERE ORDER BY
    List<Member> findByNameContainingOrderByNameAsc(String name);

    // JPQL (Java Persistence Query Language)
    @Query("SELECT m FROM Member m WHERE m.name = :name")
    List<Member> findMemberByName(@Param("name") String name);
    @Query("SELECT m FROM Member m WHERE m.name = :name AND m.email = :email")
    List<Member> findMemberByNameEmail(@Param("name") String name, @Param("email") String email);
    @Query("SELECT m FROM Member m WHERE m.enabled = :active AND m.age >= 19 AND m.email IS NOT NULL ORDER BY m.name")
    List<Member> getActiveAdultWithEmail(@Param("active") boolean active);
    @Query(value = "SELECT * FROM member WHERE enabled = :active AND age >= 19 AND email IS NOT NULL ORDER BY name", nativeQuery = true)
    List<Member> getActiveAdultWithEmailByNative(@Param("active") boolean active);
}