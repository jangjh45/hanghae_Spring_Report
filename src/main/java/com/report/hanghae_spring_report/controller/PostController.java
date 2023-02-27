package com.report.hanghae_spring_report.controller;

import com.report.hanghae_spring_report.dto.PostDto;
import com.report.hanghae_spring_report.entity.Post;
import com.report.hanghae_spring_report.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/api/posts") // 게시글 저장 name content password
    public Post createPost(@RequestBody PostDto postDto) {
        System.out.println(postDto.toString());
        return postService.createPost(postDto);
    }

    @GetMapping("/api/posts") // 게시글 전체 조회
    public List<Post> getPostList() {
        return postService.getPostList();
    }

    @GetMapping("/api/posts/{id}") // 게시글 단건 조회
    public Post getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }
}
