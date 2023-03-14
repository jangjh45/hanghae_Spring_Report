package com.report.hanghae_spring_report.config;

import com.report.hanghae_spring_report.jwt.JwtAuthFilter;
import com.report.hanghae_spring_report.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
// 스프링 Security 지원을 가능하게 함
@EnableWebSecurity
// 메소드 보안 활성화
// @Secured 어노테이션 활성화
// 메소드 레벨에서 권한(Authority)을 설정할 수 있다.
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 비밀번호 암호화 기능 등록
        // 적응형 단방향 함수를 사용
        // 단방향은 평문에서 암호화가 가능하지만 다시 평문으로 돌리는 것은 불가능하다.
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // h2-console 사용 및 resources 접근 허용 설정
        // 밑에 있는 SecurityFilterChain 보다 우선적으로 걸리는 설정이다.
        // h2-console 사용 및 resources 접근 허용 설정
        return (web) -> web.ignoring() // ignoring 인증처리를 무시하겠다.
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        // CSRF(Cross-Site Request Forgery, 사이트 간 요청 위조)
        // 웹 애플리케이션 취약점 중 하나로, 인증된 사용자의 권한을 사용하여 악성 요청을 전송하는 공격
        http.csrf().disable();

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                // 로그인, 회원가입 기능 인증없이 허용
                .antMatchers("/post/user/**").permitAll()

                // 게시글 전체, 단건 조회는 인증없이 허용
                .antMatchers("/posts/list/**").permitAll()

                // 이외의 URL요청들은 전부 다 authenticated 인증 처리를 하겠다.
                .anyRequest().authenticated()

                // JWT 인증/인가를 사용하기 위한 설정
                // Custom Filter 등록하기
                // addFilterBefore - (어떠한 Filter 이전에 Filter를 먼저 실행 및 추가하겠다.)
                // 즉 JwtAuthFilter를 UsernamePasswordAuthenticationFilter 실행 전에 사용하도록 설정
                // UsernamePasswordAuthenticationFilter 이것으로 인증처리가 되지만
                // JWT토큰을 사용하는 시큐리티 커스텀 필터를 사용할 것이다.
                .and().addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
        // UsernamePasswordAuthenticationFilter
        // Form Login 기반이 사용되고 이것은 인증이 필요한 URL요청이 들어왔을 때
        // username 과 password 가 인증이 안되어 있으면 로그인페이지가 반환


        // 내장 기본 로그인 사용
//        http.formLogin();
        // Custom 로그인 페이지 사용
//        http.formLogin().loginPage("/api/user/login-page").permitAll();
        // 접근 제한 페이지 이동 설정
//        http.exceptionHandling().accessDeniedPage("/api/user/forbidden");

        return http.build();
    }
}
