package com.example.dnd_13th_9_be.global.error;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import com.example.dnd_13th_9_be.global.response.ResponseCode;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum ErrorCode implements ResponseCode {
  // client error
  BAD_REQUEST(HttpStatus.BAD_REQUEST, "40000", "요청이 올바르지 않습니다"),
  VALIDATION_ERROR(HttpStatus.UNPROCESSABLE_ENTITY, "40001", "검증 오류가 발생했습니다"),
  NOT_FOUND(HttpStatus.NOT_FOUND, "40001", "대상을 찾을 수 없습니다"),

  // server error
  INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "50000", "서버 내부 오류입니다"),
  SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "50001", "일시적으로 이용할 수 없습니다"),
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;

  @Override
  public HttpStatus httpStatus() {
    return status;
  }

  @Override
  public String code() {
    return code;
  }

  @Override
  public String defaultMessage() {
    return message;
  }
}
