package com.report.hanghae_spring_report.controller;

import com.report.hanghae_spring_report.dto.PostListResponseDto;
import com.report.hanghae_spring_report.dto.PostRequestDto;
import com.report.hanghae_spring_report.dto.PostResponseDto;
import com.report.hanghae_spring_report.security.UserDetailsImpl;
import com.report.hanghae_spring_report.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    /**
     * @AuthenticationPrincipal 컨트롤러 메서드의 파라미터로 현재 인증된 사용자의 정보를 주입
     */

    // 게시글 저장 name content password
    @PostMapping("/create")
    public PostResponseDto createPost(
            @RequestBody PostRequestDto postRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.createPost(postRequestDto, userDetails.getUser());
    }

    // 게시글 전체 조회
    @GetMapping("/list")
    public List<PostListResponseDto> getPostList() {
        return postService.getPostList();
    }

    // 게시글 단건 조회
    @GetMapping("/list/{id}")
    public PostListResponseDto getPost(
            @PathVariable Long id) {
        return postService.getPost(id);
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public PostResponseDto updatePost(
            @PathVariable Long id,
            @RequestBody PostRequestDto postRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.updatePost(id, postRequestDto, userDetails.getUser());
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity deletePost(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(postService.deletePost(id, userDetails.getUser()));
    }

    // 게시글 좋아요
    @PostMapping("/post/{postid}")
    public ResponseEntity GoodPost(
            @PathVariable Long postid,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(postService.postLike(postid, userDetails));
    }
}
