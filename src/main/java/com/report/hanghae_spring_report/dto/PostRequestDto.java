package com.report.hanghae_spring_report.dto;

import javax.validation.constraints.NotNull;

public class PostRequestDto {
    @NotNull(message = "제목 입력은 필수 입니다.")
    private String title;
    @NotNull(message = "내용 입력도 필수 입니다.")
    private String contents;

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

}
