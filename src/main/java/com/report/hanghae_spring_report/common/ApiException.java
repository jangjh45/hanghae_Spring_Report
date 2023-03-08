package com.report.hanghae_spring_report.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
// Lombok 라이브러리에서 제공하는 어노테이션으로, 모든 필드를 인자로 받는 생성자를 자동으로 생성
@AllArgsConstructor
// RuntimeException 클래스를 상속
// 예외가 발생할 경우에 해당 예외가 런타임 예외로 처리
// 예외를 던질 때 try-catch 블록을 작성하지 않아도 되는 등의 장점
public class ApiException extends RuntimeException {
    // ApiException 클래스는 ExceptionEnum 타입의 필드 errorCode를 가지고 있다.
    // 해당 예외가 발생한 이유를 나타낸다.
    // errorCode 필드에 HTTP 상태 코드를 저장
    private final ExceptionEnum errorCode;
}
