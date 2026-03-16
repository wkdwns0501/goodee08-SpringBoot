package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

// 스프링 시큐리티 관련 설정을 위한 빈을 정의할 수 있는 클래스
@Configuration
public class SecurityConfig {

    @Bean
    // 요청별 접근 권한과 로그인 방식을 설정
    public SecurityFilterChain securityFilterChain(HttpSecurity http, RememberMeServices rememberMeServices) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/", "/home").permitAll() // 모든 사용자에게 허용
            .requestMatchers("/member/**").hasAuthority("ROLE_ADMIN") // 로그인한 사용자 중에 해당 권한이 있는 사용자만 접근 허용
            .anyRequest().authenticated() // 그 외의 모든 요청은 인증된 사용자만 접근 허용
        ) // 즉, 더 구체적인 규칙을 먼저, 더 넓은 규칙을 나중에 써야함
            .rememberMe(remember -> remember
                        .rememberMeServices(rememberMeServices))
            .formLogin(login -> login
                    .loginPage("/login")
                    .defaultSuccessUrl("/")
                    . permitAll())
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/home")
                    .permitAll());
        return http.build();
    }

    @Bean
    // 자동 로그인 토큰을 DB에 저장하는 저장소를 만드는 메소드
    public PersistentTokenRepository persistentTokenRepository(DataSource dataSource) {
        // 스프링 시큐리티에서 기본적으로 제공하는 토큰 저장을 위한 클래스
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource); // 토큰을 저장/조회할 DB 연결 설정
        return repository;
    }

    @Bean
    // 자동 로그인 토큰 발급과 검증에 사용할 서비스를 등록
    public RememberMeServices rememberMeServices(UserDetailsService userDetailsService, PersistentTokenRepository tokenRepository) {
        return new PersistentTokenBasedRememberMeServices(
                "myRememberMeKey", // 토큰 생성시 사용되는 고유한 비밀 키
                userDetailsService, // 사용자 정보를 조회하기 위해
                tokenRepository // 토큰을 저장하기 위해
        );
    }

    // 임시 1: 메모리 기반 사용자 인증
//    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.builder()
                .username("user")
//                .password("{noop}qwe123") // {noop}은 패스워드 인코딩을 하지 않겠다는 의미
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
//                .password("{noop}qwe123")
                .password(passwordEncoder.encode("password"))
                .roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    // 임시 2: JDBC(DB) 기반 사용자 인증
//    @Bean
    public UserDetailsService userDetailsServiceJdbc(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    // 로그인 비밀번호 비교에 사용할 암호화 방식을 등록
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 스프링 시큐리티에서 무시해야 할 패턴을 등록
    // 정적 리소스 또는 필요에 따라 h2-console과 같은 패턴을 무시하도록 설정
    // CustomWebSecurityCustomizer와 같은 기능
    @Bean
    // 정적 리소스와 H2 콘솔은 시큐리티 검사에서 제외
    public WebSecurityCustomizer webSecurityCustomizer() {
//        return new WebSecurityCustomizer() {
//            @Override
//            public void customize(WebSecurity web) {
//                web.ignoring().requestMatchers(
//                        "/h2-console/**",
//                        "/css/**",
//                        "/js/**",
//                        "/image/**"
//                );
//            }
//        };

        // WebSecurityCustomizer는 함수형 인터페이스이므로 람다식으로 간단히 표현할 수 있음
        return web -> web.ignoring().requestMatchers(
                "/h2-console/**",
                "/css/**",
                "/js/**",
                "/image/**"
        );
    }

}
