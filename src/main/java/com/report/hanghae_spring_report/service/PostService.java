package com.report.hanghae_spring_report.service;

import com.report.hanghae_spring_report.dto.*;
import com.report.hanghae_spring_report.entity.Post;
import com.report.hanghae_spring_report.entity.User;
import com.report.hanghae_spring_report.jwt.JwtUtil;
import com.report.hanghae_spring_report.repository.PostRepository;
import com.report.hanghae_spring_report.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 게시글 등록 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) { // JWT의 유효성을 검증하여 올바른 JWT인지 확인??
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 요청받은 DTO로 DB에 저장할 객체 만들기, 토큰에 있는 작성자 이름을 같이 넣음
            Post post = postRepository.saveAndFlush(new Post(postRequestDto, claims.getSubject()));
            return new PostResponseDto(post);
        } else {
            throw new IllegalArgumentException("로그인 안함(토큰 없음)");

        }
    }

    @Transactional(readOnly = true)
    public List<PostListResponseDto> getPostList() {
        // PostResponseDto 객체만 들어올 수 있는 리스트 생성
        List<PostListResponseDto> postResponseDtoList = new ArrayList<>();
        // 데이터 베이스에서 찾은 모든값을 리스트로 저장
        List<Post> postList = postRepository.findAllByOrderByIdDesc();
        for (Post post : postList) { // 리스트에서 하나씩 꺼내서 postResponseDtoList 리스트에 저장
            postResponseDtoList.add(new PostListResponseDto(post));
            log.info("post = {}", post);
        }
        return postResponseDtoList;
    }

    @Transactional(readOnly = true)
    public PostListResponseDto getPost(Long id) {
        Post post = checkPost(id);
        return new PostListResponseDto(post);
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 수정가능
        if (token != null) {
            // JWT의 유효성을 검증하여 올바른 토큰인지 확인
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 게시글에 일치하는 게시글 아이디와 작성자 이름이 있는지 확인
            Post post = postRepository.findByIdAndUsername(id, claims.getSubject()).orElseThrow(
                    () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
            );

            post.update(postRequestDto);
            return new PostResponseDto(post);
        } else {
            throw new IllegalArgumentException("로그인 안함(토큰 없음)");
        }
    }

    @Transactional
    public MessageResponse deletePost(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 수정가능
        if (token != null) {
            // JWT의 유효성을 검증하여 올바른 토큰인지 확인
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Post post = postRepository.findByIdAndUsername(id, claims.getSubject()).orElseThrow(
                    () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
            );

            postRepository.deleteById(id);
            return new MessageResponse(StatusEnum.OK);
        } else {
            throw new IllegalArgumentException("로그인 안함(토큰 없음)");
        }
    }

    private Post checkPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("일치하는 게시글 없음")
        );
    }
}
