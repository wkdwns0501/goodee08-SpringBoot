package com.example.demo.repository;

import com.example.demo.entity.Member;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("회원 리포지터리 테스트")
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;

    // 테스트 시작 전 회원 리포지터리에 초기 데이터 생성
    @BeforeEach
    public void doBeforeEach() {
        // 각 테스트 전에 수행되어야 할 작업
        memberRepository.save(Member.builder().name("윤서준").email("SeojunYoon@goodee.co.kr").age(10).enabled(true).build());
        memberRepository.save(Member.builder().name("윤광철").email("KwangcheolYoon@goodee.co.kr").age(43).enabled(true).build());
        memberRepository.save(Member.builder().name("공미영").email("MiyeongKong@goodee.co.kr").age(26).enabled(false).build());
        memberRepository.save(Member.builder().name("김도윤").email("DoyunKim@goodee.co.kr").age(10).enabled(true).build());
    }

    // 각 테스트가 완료된 후에는 회원 리포지터리 삭제
    @AfterEach
    public void doAfterEach() {
        // 각 테스트가 종료된 후에 수행되어야 할 작업
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("조건 검색 테스트")
    public void testUserCase1() {
        // 첫 번째 테스트 코드

        // 회원 리포지터리에 저장된 개수(회원 수)가 4인지 검증
        assertThat(memberRepository.count()).isEqualTo(4);
                // 실제 값                            // 기대 값

        // '윤서준'이라는 이름으로 검색된 결과 개수가 1인지 검증
        assertThat(memberRepository.findByName("윤서준").size()).isEqualTo(1);

        // 이름이 '윤서준'이고 이메일이 'SeojunYoon@goodee.co.kr'인 사용자를 조회한 결과 개수가 1인지 검증
        assertThat(memberRepository.findByNameAndEmail("윤서준", "SeojunYoon@goodee.co.kr").size())
                .isEqualTo(1);

        // 이름이 '윤서준'이거나 또는 이메일이 'KwangcheolYoon@goodee.co.kr'인 사용자를 조회한 결과 개수가 1인지 검증
        assertThat(memberRepository.findByNameOrEmail("윤서준", "SeojunYoon@goodee.co.kr").size())
                .isEqualTo(1);

        // 이름에 '윤'이라는 글자가 포함된 사용자를 조회한 결과 개수가 3인지 검증
        assertThat(memberRepository.findByNameContaining("윤").size()).isEqualTo(3);

        // 이름이 '영'으로 끝나는 사람을 조회한 결과 개수가 1인지 검증
        assertThat(memberRepository.findByNameLike("%영").size()).isEqualTo(1);

        // 나이가 26를 초과하는 사람의 수가 1인지 검증
        assertThat(memberRepository.findByAgeGreaterThan(26).size()).isEqualTo(1);

        // 나이가 26세 이상인 사람의 수가 2인지 검증
        assertThat(memberRepository.findByAgeGreaterThanEqual(26).size()).isEqualTo(2);

        // 나이가 26세 미만인 사람의 수가 2인지 검증
        assertThat(memberRepository.findByAgeLessThan(26).size()).isEqualTo(2);

        // 나이가 26세 이하인 사람의 수가 3인지 검증
        assertThat(memberRepository.findByAgeLessThanEqual(26).size()).isEqualTo(3);
    }

//    @Test
    @RepeatedTest(value = 3, name="테스트 {displayName} 중 {currentRepetition} of {totalRepetitions}")
    @DisplayName("정렬 순서 테스트")
    public void testUserCase2() {
        // 두 번째 테스트 코드

        // 사용자 이름 순으로 조회를 한 결과 개수가 4인지 검증
        assertThat(memberRepository.findAllByOrderByNameAsc().size()).isEqualTo(4);

        // 사용자 이름 순으로 조회를 한 첫번째 사람의 이름이 '공미영'인지 검증
        assertThat(memberRepository.findAllByOrderByNameAsc().getFirst().getName()).isEqualTo("공미영");
    }

    @Test
    @DisplayName("JPQL 테스트")
//    @Disabled("잠시 테스트 중단")
    public void testUserCase3() {
        // 세 번째 테스트 코드

        // 이름이 '윤서준'인 사람의 수가 1인지 검증
        assertThat(memberRepository.findMemberByName("윤서준").size()).isEqualTo(1);

        // 이름이 '윤서준'이고 이메일이 'SeojunYoon@goodee.co.kr'인 사람의 수가 1인지 검증
        assertThat(memberRepository.findMemberByNameEmail("윤서준", "SeojunYoon@goodee.co.kr").size()).isEqualTo(1);

        // 사용자가 활성화(enabled = true)되어 있고 나이가 19세 이상이고 이메일이 있는 사람의 수가 1인지 검증 - JPQL 사용
        assertThat(memberRepository.getActiveAdultWithEmail(true).size()).isEqualTo(1);

        // 사용자가 활성화(enabled = true)되어 있고 나이가 19세 이상이고 이메일이 있는 사람의 수가 1인지 검증 - Native SQL 사용
        assertThat(memberRepository.getActiveAdultWithEmailByNative(true).size()).isEqualTo(1);
    }
}
