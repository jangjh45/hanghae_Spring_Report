package com.report.hanghae_spring_report.dto;

public class MessageResponse {

    private int status;
    private String messege;

    public MessageResponse(StatusEnum statusEnum) {
        this.status = statusEnum.statusCode;
        this.messege = statusEnum.msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessege() {
        return messege;
    }

    public void setMessege(String messege) {
        this.messege = messege;
    }

}
