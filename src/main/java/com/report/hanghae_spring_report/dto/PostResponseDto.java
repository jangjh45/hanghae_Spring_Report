package com.report.hanghae_spring_report.dto;

import com.report.hanghae_spring_report.entity.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private Long id;
    private String username;
    private String password;
    private String title;
    private String contents;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.username = post.getUsername();
        this.password = post.getPassword();
        this.title = post.getTitle();
        this.contents = post.getContents();
    }
}
