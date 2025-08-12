package com.example.dnd_13th_9_be.global.error;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import com.example.dnd_13th_9_be.global.response.ResponseCode;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum ErrorCode implements ResponseCode {
  // client error
  BAD_REQUEST(HttpStatus.BAD_REQUEST, "40000", "요청이 올바르지 않습니다"),
  VALIDATION_ERROR(HttpStatus.UNPROCESSABLE_ENTITY, "42200", "검증 오류가 발생했습니다"),
  NOT_FOUND(HttpStatus.NOT_FOUND, "40400", "대상을 찾을 수 없습니다"),

  // server error,
  INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "50000", "서버 내부 오류입니다"),
  SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "50300", "일시적으로 이용할 수 없습니다"),

  // user 6xxxx
  NOT_FOUND_USER(HttpStatus.NOT_FOUND, "60401", "사용자를 찾을 수 없습니다"),

  // plan 71xxx,
  NOT_FOUND_PLAN(HttpStatus.NOT_FOUND, "71000", "유효하지 않은 계획입니다"),
  PLAN_CREATION_LIMIT(HttpStatus.BAD_REQUEST, "71001", "최대 생성할 수 있는 계획 갯수를 초과했습니다"),
  PLAN_RENAME_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "71002", "계획 이름 변경에 실패했습니다"),
  PLAN_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "71003", "계획 삭제에 실패했습니다"),
  DEFAULT_PLAN_CANNOT_BE_DELETE(HttpStatus.FORBIDDEN, "71004", "기본 계획은 삭제 할 수 없습니다"),

  // folder 72xxx
  NOT_FOUND_FOLDER(HttpStatus.NOT_FOUND, "72000", "유효하지 않은 폴더입니다"),

  FOLDER_CREATION_LIMIT(HttpStatus.BAD_REQUEST, "72001", "최대 생성할 수 있는 폴더 갯수를 초과했습니다"),
  FOLDER_RENAME_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "72002", "폴더 이름 변경에 실패했습니다"),
  FOLDER_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "72003", "폴더 삭제에 실패했습니다"),

  DEFAULT_FOLDER_CANNOT_BE_DELETE(HttpStatus.FORBIDDEN, "72004", "기본 폴더는 삭제 할 수 없습니다"),
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
