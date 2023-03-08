package com.report.hanghae_spring_report.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CommentRequestDto {
    @NotNull(message = "댓글을 입력해 주세요")
    private String comment;
}
