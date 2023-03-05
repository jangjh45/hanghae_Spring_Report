package com.report.hanghae_spring_report.dto;

import com.report.hanghae_spring_report.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostListResponseDto {
    private String username;
    private String title;
    private String contents;
    private LocalDateTime createdAt;

    public PostListResponseDto(Post post) {
        this.username = post.getUsername();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
    }
}
