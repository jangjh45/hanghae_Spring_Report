package com.report.hanghae_spring_report.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PostDto {
    private String username;
    private String password;
    private String title;
    private String contents;
}
