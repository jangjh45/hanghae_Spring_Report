package com.report.hanghae_spring_report.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PostRequestDto {
    private String username;
    private String title;
    private String contents;
}
