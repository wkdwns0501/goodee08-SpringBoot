package com.example.jpa.repository;

import com.example.jpa.dto.MemberStats;
import com.example.jpa.dto.MemberStatsNative;
import com.example.jpa.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Spring Data JPA가 JpaRepository를 확장한 인터페이스를 자동으로 구현 객체로 만들고 스프링 빈 객체로 등록
// 생략 가능하지만 해당 인터페이스가 리포지터리라는 사실을 명시적으로 나타내는 것을 권장
@Repository
// JpaRepository<Member,Long>
// Member: 리포지터리가 관리하는 엔티티 타입 -> Member 엔티티에 대한 CRUD
// Long: 해당 엔티티의 아이디(PK) 타입
public interface MemberRepository extends JpaRepository<Member,Long> {

    // Query Method 방식
    // 1. 검색 명명 규칙: findBy + 컬럼 이름(엔티티의 필드명 기준)
    // 이름으로 회원 조회
    List<Member> findAllByName(String name);
    List<Member> findByName(String name); // 주로 사용
    List<Member> findByNameIs(String name); // Is 생략 가능
    List<Member> findByNameEquals(String name); // Equals 생략 가능

    // (참고) find 이외에도 get, query, read, search 사용 가능
    List<Member> getByName(String name);
    List<Member> queryByName(String name);
    List<Member> readByName(String name);
    List<Member> searchByName(String name);

    // 2. 두 가지 이상의 조건 검색
    // 이름과 이메일을 AND 조건으로 회원 조회
    List<Member> findByNameAndEmail(String name, String email);
    // 이름과 이메일을 OR 조건으로 회원 조회
    List<Member> findByNameOrEmail(String name, String email);

    // 이름의 시작으로 회원 조회("윤%")
    List<Member> findByNameStartingWith(String name);
    // 이름의 마지막으로 회원 조회("%윤")
    List<Member> findByNameEndingWith(String name);
    // 이름을 포함하는 회원 조회("%윤%")
    // 예: findByNameContaining("윤");
    List<Member> findByNameContaining(String name);
    // 이름을 포함하는 회원 조회로 LIKE 검색을 위한 와일드 카드(%, _)를 직접 사용
    // 예: findByNameLike("%윤%");
    List<Member> findByNameLike(String name);

    // 3. 크기 비교
    // 나이 정보가 존재하지 않는 회원 조회
    List<Member> findByAgeIsNull();
    // 나이 정보가 존재하는 회원 조회
    List<Member> findByAgeIsNotNull();
    // 매개변수로 전달된 나이로 회원 조회
    List<Member> findByAgeIs(Integer age);
    List<Member> findByAge(Integer age); // Is 생략 가능
    // 매개변수로 전달된 나이보다 나이가 더 많은 회원 조회
    List<Member> findByAgeGreaterThan(Integer age); // > (Equal 붙이면 >=)
    List<Member> findByAgeAfter(Integer age); // After는 주로 날짜/시간 타입 비교에 사용
    // 매개변수로 전달된 나이보다 나이가 더 적은 회원 조회
    List<Member> findByAgeLessThan(Integer age); // < (Equal 붙이면 <=)
    List<Member> findByAgeBefore(Integer age); // Before는 주로 날짜/시간 타입 비교에 사용
    // 매개변수로 전달된 나이보다 나이가 더 적거나 같은 회원 조회
    List<Member> findByAgeLessThanEqual(Integer age); // <=
    // 매개변수로 전달된 나이를 포함해 그 사이 나이의 회원 조회
    List<Member> findByAgeBetween(Integer min, Integer max);

    // (Quiz) Query Method 작성
    // SELECT *
    // FROM member
    // WHERE name = ‘…’ AND email = ‘…’ OR age > …
    List<Member> findByNameAndEmailOrAgeGreaterThan(String name, String email, Integer age);

    // 4. 정렬 순서
    // 이름순으로 조회
    List<Member> findByOrderByNameAsc();
    // 이름의 역순으로 조회
    List<Member> findByOrderByNameDesc();
    // 이름순으로 조회하고 이름이 같은 경우에는 나이의 역순으로 조회
    List<Member> findByOrderByNameAscAgeDesc();
    // 이름의 일부분으로 검색하고 그 결과는 이름순으로 조회
    // 조건과 정렬 방법 등이 함께 이름에 사용되면 가독성이 떨어짐
    List<Member> findByNameContainingOrderByNameAsc(String name);
    // 나이순으로 정렬하고 나이가 가장 적은 한 명을 조회(LIMIT 1)
    Member findTopByOrderByAgeAsc();
    Member findFirstByOrderByAgeAsc();
    // 나이순으로 정렬하고 나이가 가장 적은 두 명을 조회(LIMIT 2)
    List<Member> findTop2ByOrderByAgeAsc();
    List<Member> findFirst2ByOrderByAgeAsc();

    // 정렬 조건을 분리하는 방식(권장)
    // Sort 객체와 Pageable 객체를 매개변수로 검색하는 메솓,
    // 이름의 일부분으로 검색하고 Sort 객체의 정보를 기반으로 정렬
    // Sort: 정렬만 필요할 때
    List<Member> findByNameContaining(String name, Sort sort);

    // 이름의 일부분으로 검색하고 Pageable 객체의 정보를 기반으로 페이지 조회
    // Pageable: 페이징 + 정렬이 필요할 때
    Page<Member> findByNameContaining(String name, Pageable pageable);
    // Page: 조회 결과 + 페이징 정보까찌 포함한 객체

    // 5. 삭제
    // 이메일을 사용해 회원 삭제
    @Transactional
    int deleteByEmail(String email);

    // 이름을 사용해 회원 삭제
    @Transactional
    int deleteByName(String name);

    // JPQL 방식
    // JPQL은 객체 지향 쿼리이지 SQL이 아님
    // JPQL 작성 시 테이블과 컬럼 이름 대신 엔티티에서 정의한 클래스 이름과 속성명을 사용
    // 대소문자르 구분하고 엔티티 이름은 반드시 별칭 사용

    // 파라미터에 따라 이름으로 검색하거나 이름과 이메일 둘 다 사용해 검색하는 JPQL 매핑
    // @Query: 리포지터리 메소드와 JPQL을 매핑
    // @Param: 파라미터를 JPQL로 전달하고, JPQL에서는 콜론(:)을 사용해 받음
    @Query("SELECT m FROM Member m WHERE m.name = :name")
    List<Member> findMember(@Param("name") String name);

    @Query("SELECT m FROM Member m WHERE m.name = :name AND m.email = :email")
    List<Member> findMember(@Param("name") String name, @Param("email") String email);

    // JPQL로 JOIN 쿼리 작성하기
    // 회원의 이름, 이메일 그리고 회원이 작성한 게시글의 개수를 조회
    // 쿼리한 결과는 객체 배열 Object[]로 받아 사용하면 됨
    @Query("""
            SELECT m.name, m.email, COUNT(a.id) as count
            FROM Member m 
            LEFT JOIN Article a ON a.member = m
            GROUP BY m
            ORDER BY count DESC
    """)
    List<Object[]> getMemberStatsObject();
    // Object[0]: m.name
    // Object[1]: m.email
    // Object[2]: COUNT(a.id)

    // 직접 생성한 회원 통계 객체로 변환
    // JPQL의 new DTO(...)는 생성자 파라미터 순서로 매핑하므로 별칭(as)은 보통 필요 없음
    // 현재 COUNT(a.id) as count는 Hibernate에서 동작할 수 있지만, 구현체 차이를 줄이려면 COUNT(a.id)만 쓰는 편이 더 안전
    @Query("""
            SELECT new com.example.jpa.dto.MemberStats(m.name, m.email, COUNT(a.id) as count)
            FROM Member m
            LEFT JOIN Article a ON a.member = m
            GROUP BY m
            ORDER BY count DESC
    """)
    List<MemberStats> getMemberStats();

    // 전체 회원에 대해 나이를 특정한 값으로 수정
//    @Modifying // UPDATE/DELETE 데이터를 변경하는 쿼리임을 명시적으로 알려줌 -> executeUpdate()
    @Modifying(clearAutomatically = true) // 벌크 작업 이후에 영속성 컨텍스트에 캐시되어 있는 엔티티들을 clear
    @Query("UPDATE Member m SET m.age = :age") // @Query는 기본적으로 조회용 -> executeQuery()
    @Transactional // 원래는 서비스에 적용
    int setMemberAge(Integer age);

    // 네이티브 SQL 쿼리 작성하기
    // JPQL 예제와 동일한 동작을 하는 메소드
    // SQL이므로 엔티티 이름 대신 실제 테이블 이름과 컬럼 이름을 사용해야 함
    // 매개변수를 사용할 때는 :name과 같이 사용하고, @Param으로 전달
    @Query(value = """
            SELECT m.name AS name, m.email AS email, COUNT(a.id) AS count
            FROM member m
            LEFT JOIN article a ON m.id = a.member_id
            GROUP BY m.id, m.name, m.email
            ORDER BY count DESC
    """, nativeQuery = true)
    List<Object[]> getMemberStatsNativeObject();

    // JPQL에서와 같이 객체 배열 대신 직접 회원 통계 객체를 반환하도록 할 수 있음
    // 네이티브 SQL에서는 JPQL의 new 명령어를 사용할 수 없으므로, 반환 타입이 인터페이스인 경우에만 매핑이 가능
    @Query(value = """
            SELECT m.name AS name, m.email AS email, COUNT(a.id) AS count
            FROM member m
            LEFT JOIN article a ON m.id = a.member_id
            GROUP BY m.id, m.name, m.email
            ORDER BY count DESC
    """, nativeQuery = true)
    List<MemberStatsNative> getMemberNativeStats();

}
