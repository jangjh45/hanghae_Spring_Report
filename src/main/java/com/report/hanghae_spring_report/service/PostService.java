package com.report.hanghae_spring_report.service;

import com.report.hanghae_spring_report.common.ApiException;
import com.report.hanghae_spring_report.common.ExceptionEnum;
import com.report.hanghae_spring_report.dto.*;
import com.report.hanghae_spring_report.entity.Post;
import com.report.hanghae_spring_report.entity.User;
import com.report.hanghae_spring_report.entity.UserRoleEnum;
import com.report.hanghae_spring_report.jwt.JwtUtil;
import com.report.hanghae_spring_report.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;

    // 관리자 계정만 모든 게시글 수정, 삭제 가능
    public Post getPostAdminInfo(Long id, User user) {
        Post post;
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            // 관리자 계정이기 때문에 게시글 아이디만 일치하면 수정,삭제 가능
            post = postRepository.findById(id).orElseThrow(
                    () -> new ApiException(ExceptionEnum.NOT_FOUND_POST_ADMIN)
            );
        } else {
            // 사용자 계정이므로 게시글 아이디와 작성자 이름이 있는지 확인하고 있으면 수정,삭제 가능
            post = postRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new ApiException(ExceptionEnum.NOT_FOUND_POST)
            );
        }
        return post;
    }

    // 게시글 저장
    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto,
                                      User user) {

        // 요청받은 DTO로 DB에 저장할 객체 만들기, 토큰에 있는 작성자 이름을 같이 넣음
        // 새로운 post객체를 생성하여 저장하는 데 사용한다.
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
        }
        return postResponseDtoList;
    }

    // 게시글 단건 조회
    @Transactional(readOnly = true)
    public PostListResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ApiException(ExceptionEnum.NOT_FOUND_POST_ALL)
        );
        return new PostListResponseDto(post);
    }

    // 게시글 수정
    @Transactional
    public PostResponseDto updatePost(Long id,
                                      PostRequestDto postRequestDto,
                                      User user) {

        Post post = getPostAdminInfo(id, user);
        post.update(postRequestDto); // 이미 존재하는 post객체를 수정하고 업데이트하는 데 사용한다.
        return new PostResponseDto(post);
    }

    // 게시글 삭제
    @Transactional
    public MessageResponse deletePost(Long id,
                                      User user) {

        getPostAdminInfo(id, user);
        postRepository.deleteById(id);
        return new MessageResponse(StatusEnum.OK);
    }

}