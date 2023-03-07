package com.report.hanghae_spring_report.jwt;


import com.report.hanghae_spring_report.entity.Comment;
import com.report.hanghae_spring_report.entity.Post;
import com.report.hanghae_spring_report.entity.User;
import com.report.hanghae_spring_report.entity.UserEnum;
import com.report.hanghae_spring_report.repository.CommentRepository;
import com.report.hanghae_spring_report.repository.PostRepository;
import com.report.hanghae_spring_report.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_KEY = "auth";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final long TOKEN_TIME = 60 * 60 * 1000L;

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // header 토큰을 가져오기
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰 생성
    public String createToken(String username, UserEnum role) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .claim(AUTHORIZATION_KEY, role)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    // 토큰 검증과 사용자 존재여부 확인 함수
    public User getUserInfo(HttpServletRequest request) {
        String token = resolveToken(request);
        Claims claims;
        User user = null;

        if (token != null) {
            // JWT의 유효성을 검증하여 올바른 JWT인지 확인
            if (validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            return user;
        }
        throw new IllegalArgumentException("로그인 안함(토큰 없음)");
    }

    // 관리자 계정만 모든 게시글 수정, 삭제 가능
    public Post getPostAdminInfo(Long id, User user) {
        Post post;
        if (user.getRole().equals(UserEnum.ADMIN)) {
            // 관리자 계정이기 때문에 게시글 아이디만 일치하면 수정,삭제 가능
            post = postRepository.findById(id).orElseThrow(
                    () -> new NullPointerException("(관리자)해당 게시글이 존재하지 않습니다.")
            );
        } else {
            // 사용자 계정이므로 게시글 아이디와 작성자 이름이 있는지 확인하고 있으면 수정,삭제 가능
            post = postRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new NullPointerException("(사용자)해당 게시글이 존재하지 않습니다.")
            );
        }
        return post;
    }

    // 관리자 계정만 모든 댓글 수정, 삭제 가능
    public Comment getCommentAdminInfo(Long id, User user) {
        Comment comment;
        if (user.getRole().equals(UserEnum.ADMIN)) {
            // 관리자 계정이기 때문에 게시글 아이디만 일치하면 수정,삭제 가능
            comment = commentRepository.findById(id).orElseThrow(
                    () -> new NullPointerException("(관리자)해당 게시글이 존재하지 않습니다.")
            );
        } else {
            // 사용자 계정이므로 게시글 아이디와 작성자 이름이 있는지 확인하고 있으면 수정,삭제 가능
            comment = commentRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new NullPointerException("(사용자)해당 게시글이 존재하지 않습니다.")
            );
        }
        return comment;
    }
}