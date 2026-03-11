package com.example.jpa;

import com.example.jpa.dto.MemberStats;
import com.example.jpa.entity.Article;
import com.example.jpa.entity.Member;
import com.example.jpa.repository.ArticleRepository;
import com.example.jpa.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class JpaApplicationTests {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private ArticleRepository articleRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void getMemberStats_returnsAggregatedArticleCount() {
		String email = "native-test-" + UUID.randomUUID() + "@example.com";

		Member member = Member.builder()
				.name("native-test-member")
				.email(email)
				.age(20)
				.build();
		memberRepository.save(member);

		articleRepository.save(Article.builder()
				.title("first")
				.description("first article")
				.member(member)
				.build());
		articleRepository.save(Article.builder()
				.title("second")
				.description("second article")
				.member(member)
				.build());

		List<MemberStats> stats = memberRepository.getMemberStats();

		assertThat(stats)
				.anySatisfy(stat -> {
					assertThat(stat.getEmail()).isEqualTo(email);
					assertThat(stat.getName()).isEqualTo("native-test-member");
					assertThat(stat.getCount()).isEqualTo(2L);
				});
	}

}
