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

    // 토큰 검증과 사용자 존재여부 확인 함수
    public User getUserInfo(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        User user = null;

        if (token != null) {
            // JWT의 유효성을 검증하여 올바른 JWT인지 확인
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
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

    // 게시글 저장
    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto,
                                      HttpServletRequest request) {

        User user = getUserInfo(request);

        // 요청받은 DTO로 DB에 저장할 객체 만들기, 토큰에 있는 작성자 이름을 같이 넣음
        Post post = postRepository.saveAndFlush(new Post(postRequestDto, user));
        return new PostResponseDto(post);
    }

    // 게시글 전체 조회
    @Transactional(readOnly = true)
    public List<PostListResponseDto> getPostList() {
        // PostResponseDto 객체만 들어올 수 있는 리스트 생성
        List<PostListResponseDto> postResponseDtoList = new ArrayList<>();
        // 데이터 베이스에서 찾은 모든값을 리스트로 저장
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        for (Post post : postList) { // 리스트에서 하나씩 꺼내서 postResponseDtoList 리스트에 저장
            postResponseDtoList.add(new PostListResponseDto(post));
            log.info("post = {}", post);
        }
        return postResponseDtoList;
    }

    // 게시글 단건 조회
    @Transactional(readOnly = true)
    public PostListResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("일치하는 게시글 없음")
        );
        return new PostListResponseDto(post);
    }

    // 게시글 수정
    @Transactional
    public PostResponseDto updatePost(Long id,
                                      PostRequestDto postRequestDto,
                                      HttpServletRequest request) {

        User user = getUserInfo(request);

        // 게시글에 일치하는 게시글 아이디와 작성자 이름이 있는지 확인
        Post post = postRepository.findByIdAndUsername(id, user.getUsername()).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );
        post.update(postRequestDto);
        return new PostResponseDto(post);
    }

    // 게시글 삭제
    @Transactional
    public MessageResponse deletePost(Long id,
                                      HttpServletRequest request) {

        User user = getUserInfo(request);

        Post post = postRepository.findByIdAndUsername(id, user.getUsername()).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );
        postRepository.deleteById(id);
        return new MessageResponse(StatusEnum.OK);
    }
}
