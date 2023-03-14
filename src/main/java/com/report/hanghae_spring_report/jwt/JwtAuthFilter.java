package com.report.hanghae_spring_report.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.report.hanghae_spring_report.dto.SecurityExceptionDto;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter { // 기본 필터 사용됨

    private final JwtUtil jwtUtil;

    // API요청이 오면 HTTP객체가 Filter를 타고서 controller까지 온다.(HTTP객체를 파라미터로 받는다.)
    // filterChain은 Filter가 Chain형식으로 연결되어 Filter끼리 이동한다.
    // 이 Filter가 끝나고 나면 다음 Filter로 이동을 해야한다.
    // 이 Filter 마지막 줄 doFilter(request,response) 를 통해서 request와 response를 담아서 다음 필더로 이동
    // 이 Filter에서 예외처리가 되면 이전 Filter로 예외가 넘어간다.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = jwtUtil.resolveToken(request);

        // 로그인 회원가입하는 부분은 인증이 필요없다.
        // 토큰이 헤더에 없기때문에 토큰을 검증하는 부분에서 예외가 터진다.
        // 그래서 if문 분기 처리를 해준다.
        // 인증이 필요없는 것은 다음 필터로 넘어간다.
        if (token != null) {
            if (!jwtUtil.validateToken(token)) {
                // 밑에 있는 jwtExceptionHandler 를 통해서 클라이언트로 반환
                jwtExceptionHandler(response, "Token Error", HttpStatus.UNAUTHORIZED.value());
                return;
            }
            Claims info = jwtUtil.getUserInfoFromToken(token);
            setAuthentication(info.getSubject());
        }
        filterChain.doFilter(request, response);
    }

    // 인증 객체 생성
    // 인증이 만들어지면 SecurityContextHolder 에 인증이 된다.
    // SecurityContextHolder 안에 SecurityContext 인증 객체가 들어있다.
    // 다음 필터로 이동했을때 이 요청은 인증 했다고 인식해서 컨트롤러로 넘어간다.
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(username);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        // SecurityContextHolder 는 Spring Secutity 로 인증한
        // 사용자의 상세정보를 가지고있는 컨테이너이다.
    }

    // 토큰에 대한 오류가 발생했을때 클라이언트로 예외 처리 값을 알려준다.
    public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        try {
            String json = new ObjectMapper().writeValueAsString(new SecurityExceptionDto(statusCode, msg));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}