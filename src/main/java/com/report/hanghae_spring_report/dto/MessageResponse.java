package com.report.hanghae_spring_report.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse {

    private int status;
    private String messege;

    public MessageResponse(StatusEnum statusEnum) {
        this.status = statusEnum.statusCode;
        this.messege = statusEnum.msg;
    }
}
