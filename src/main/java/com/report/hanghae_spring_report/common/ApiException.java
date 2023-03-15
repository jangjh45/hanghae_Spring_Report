package com.report.hanghae_spring_report.common;

// RuntimeException 클래스를 상속
// 예외가 발생할 경우에 해당 예외가 런타임 예외로 처리
// 예외를 던질 때 try-catch 블록을 작성하지 않아도 되는 등의 장점
public class ApiException extends RuntimeException {
    // ApiException 클래스는 ExceptionEnum 타입의 필드 errorCode를 가지고 있다.
    // 해당 예외가 발생한 이유를 나타낸다.
    // errorCode 필드에 HTTP 상태 코드를 저장
    private final ExceptionEnum errorCode;

    // ExceptionEnum errorCode 값 반환
    public ExceptionEnum getErrorCode() {
        return this.errorCode;
    }

    // 예외가 터진 로직에서 ExceptionEnum 안에 있는 원하는 예외처리가 들어오고 객체 필드에 저장한다.
    public ApiException(ExceptionEnum errorCode) {
        this.errorCode = errorCode;
    }
}
