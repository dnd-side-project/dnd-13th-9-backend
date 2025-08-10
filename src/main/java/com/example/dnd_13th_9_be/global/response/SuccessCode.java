package com.example.dnd_13th_9_be.global.response;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum SuccessCode implements ResponseCode {
  SUCCESS(HttpStatus.OK, "20000", "성공했습니다"),
  CREATED(HttpStatus.CREATED, "20100", "생성되었습니다"),
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
