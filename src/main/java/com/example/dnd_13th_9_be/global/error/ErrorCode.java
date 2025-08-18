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

  // 토큰 관련 에러
  INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "40100", "유효하지 않은 토큰입니다"),
  TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "40101", "토큰이 없습니다"),
  TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "40102", "토큰이 만료되었습니다"),
  TOKEN_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "401003", "인증되지 않은 사용자입니다."),

  // 리프레시 토큰 관련 에러
  REFRESH_TOKEN_NOT_CREATED(HttpStatus.UNAUTHORIZED, "40200", "리프레시 토큰 생성에 실패했습니다."),
  INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "402001", "유효하지 않은 리프레시 토큰입니다."),
  REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "402002", "만료된 리프레시 토큰입니다."),

  // 유저 관련 에러
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "60000", "해당 id에 맞는 유저가 없습니다"),
  USER_ACCESS_DENIED(HttpStatus.FORBIDDEN, "60001", "권한이 없습니다"),

  // server error
  INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "50000", "서버 내부 오류입니다"),
  SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "50300", "일시적으로 이용할 수 없습니다"),


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

  //s3 manager 에러 80000
  INVALID_FILE_URL(HttpStatus.BAD_REQUEST, "73000", "유효하지 않은 파일 URL입니다"),
  FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "73001", "파일을 찾을 수 없습니다")
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
