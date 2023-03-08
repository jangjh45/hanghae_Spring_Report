package com.report.hanghae_spring_report.controller;

import com.report.hanghae_spring_report.dto.PostListResponseDto;
import com.report.hanghae_spring_report.dto.PostRequestDto;
import com.report.hanghae_spring_report.dto.PostResponseDto;
import com.report.hanghae_spring_report.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping("/create") // 게시글 저장 name content password
    public PostResponseDto createPost(
            @RequestBody @Valid PostRequestDto postRequestDto,
            HttpServletRequest request) {
        return postService.createPost(postRequestDto, request);
    }

    @GetMapping("/list") // 게시글 전체 조회
    public List<PostListResponseDto> getPostList() {
        return postService.getPostList();
    }

    @GetMapping("/list/{id}") // 게시글 단건 조회
    public PostListResponseDto getPost(
            @PathVariable Long id) {
        return postService.getPost(id);
    }

    @PutMapping("{id}") // 게시글 수정
    public PostResponseDto updatePost(
            @PathVariable Long id,
            @RequestBody @Valid PostRequestDto postRequestDto,
            HttpServletRequest request) {
        return postService.updatePost(id, postRequestDto, request);
    }

    @DeleteMapping("{id}") // 게시글 삭제
    public ResponseEntity deletePost(
            @PathVariable Long id,
            HttpServletRequest request) {
        return ResponseEntity.ok().body(postService.deletePost(id, request));
    }
}
