package com.report.hanghae_spring_report.controller;

import com.report.hanghae_spring_report.dto.PostListResponseDto;
import com.report.hanghae_spring_report.dto.PostRequestDto;
import com.report.hanghae_spring_report.dto.PostResponseDto;
import com.report.hanghae_spring_report.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    // 게시글 저장 name content password
    @PostMapping("/create")
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto,
                                      HttpServletRequest request) {
        return postService.createPost(postRequestDto, request);
    }

    // 게시글 전체 조회
    @GetMapping("/list")
    public List<PostListResponseDto> getPostList() {
        return postService.getPostList();
    }

    // 게시글 단건 조회
    @GetMapping("/list/{id}")
    public PostListResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public PostResponseDto updatePost(@PathVariable Long id,
                                      @RequestBody PostRequestDto postRequestDto,
                                      HttpServletRequest request) {
        return postService.updatePost(id, postRequestDto, request);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity deletePost(@PathVariable Long id,
                                     HttpServletRequest request) {
        return ResponseEntity.ok().body(postService.deletePost(id, request));
    }
}
