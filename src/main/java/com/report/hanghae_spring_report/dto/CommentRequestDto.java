package com.report.hanghae_spring_report.dto;

import javax.validation.constraints.NotNull;

public class CommentRequestDto {
    @NotNull(message = "댓글을 입력해 주세요")
    private String comment;

    public String getComment() {
        return comment;
    }

}
