package com.report.hanghae_spring_report.service;

import com.report.hanghae_spring_report.dto.*;
import com.report.hanghae_spring_report.entity.Post;
import com.report.hanghae_spring_report.entity.User;
import com.report.hanghae_spring_report.entity.UserEnum;
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

    // 게시글 저장
    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto,
                                      HttpServletRequest request) {

        User user = jwtUtil.getUserInfo(request);
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

        User user = jwtUtil.getUserInfo(request);
        Post post = jwtUtil.getAdminInfo(id, user);
        post.update(postRequestDto);
        return new PostResponseDto(post);
    }

    // 게시글 삭제
    @Transactional
    public MessageResponse deletePost(Long id,
                                      HttpServletRequest request) {

        User user = jwtUtil.getUserInfo(request);
        Post post = jwtUtil.getAdminInfo(id, user);
        postRepository.deleteById(id);
        return new MessageResponse(StatusEnum.OK);
    }
}
