package com.report.hanghae_spring_report.dto;

public enum StatusEnum {

    OK(200, "성공");


    int statusCode;
    String msg;

    StatusEnum(int statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }
}
