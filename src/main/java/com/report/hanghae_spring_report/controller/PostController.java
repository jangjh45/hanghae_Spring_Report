package com.report.hanghae_spring_report.controller;

import com.report.hanghae_spring_report.dto.PostRequestDto;
import com.report.hanghae_spring_report.dto.PostResponseDto;
import com.report.hanghae_spring_report.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping("/create") // 게시글 저장 name content password
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto) {
        log.info("postDto = {}", postRequestDto);
        return postService.createPost(postRequestDto);
    }

    @GetMapping("/list") // 게시글 전체 조회
    public List<PostResponseDto> getPostList() {
        return postService.getPostList();
    }

    @GetMapping("/list/{id}") // 게시글 단건 조회
    public PostResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @PutMapping("/update/{id}") // 게시글 수정
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto) {
        return postService.updatePost(id, postRequestDto);
    }

    @DeleteMapping("/delete/{id}") // 게시글 삭제
    public String deletePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto) {
        return postService.deletePost(id, postRequestDto);
    }
}
