package com.report.hanghae_spring_report.common;

import org.springframework.http.HttpStatus;

public enum ExceptionEnum {
    INVAILD_TOKEN(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다."),
    NOT_TOKEN(HttpStatus.BAD_REQUEST, "로그인 해주세요.(토큰없음)"),

    ONLY_ADMIN(HttpStatus.BAD_REQUEST, "관리자계정으로 삭제가능합니다."),
    DUPLICATE_USER(HttpStatus.BAD_REQUEST, "중복된 이름입니다."),

    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "회원을 찾을 수 없습니다."),
    NOT_FOUND_POST_ALL(HttpStatus.BAD_REQUEST, "게시글이 없습니다."),
    NOT_FOUND_POST(HttpStatus.BAD_REQUEST, "게시글 수정 권한이 없습니다."),
    NOT_FOUND_POST_ADMIN(HttpStatus.BAD_REQUEST, "게시물을 찾을 수 없습니다.(관리자계정)"),
    NOT_FOUND_COMMENT(HttpStatus.BAD_REQUEST, "댓글 수정 권한이 없습니다."),
    NOT_FOUND_COMMENT_ADMIN(HttpStatus.BAD_REQUEST, "댓글을 찾을 수 없습니다.(관리자계정)"),
    ;

    private HttpStatus status;
    private String message;

    public HttpStatus getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    ExceptionEnum(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
