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

  // folder & category 72xxx
  NOT_FOUND_FOLDER(HttpStatus.NOT_FOUND, "72000", "유효하지 않은 폴더입니다"),

  FOLDER_CREATION_LIMIT(HttpStatus.BAD_REQUEST, "72001", "최대 생성할 수 있는 폴더 갯수를 초과했습니다"),
  FOLDER_RENAME_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "72002", "폴더 이름 변경에 실패했습니다"),
  FOLDER_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "72003", "폴더 삭제에 실패했습니다"),

  DEFAULT_FOLDER_CANNOT_BE_DELETE(HttpStatus.FORBIDDEN, "72004", "기본 폴더는 삭제 할 수 없습니다"),
  NOT_FOUND_CATEGORY(HttpStatus.NOT_FOUND, "72005", "유효하지 않은 카테고리입니다"),

  // checklist 73xxx
  NOT_FOUND_CHECKLIST_ITEM(HttpStatus.NOT_FOUND, "73000", "존재하지 않는 체크리스트 입니다"),
  ALREADY_EXISTS_USER_REQUIRED_ITEM(HttpStatus.CONFLICT, "73001", "이미 필수 확인에 추가된 체크리스트입니다"),
  ALREADY_DELETED_USER_REQUIRED_ITEM(HttpStatus.CONFLICT, "73002", "이미 필수 확인에서 삭제된 체크리스트입니다"),

  DEFAULT_FOLDER_PLAN_NOT_CREATED(HttpStatus.CONFLICT, "61000", "기본 폴더, 플랙 생성 실패"),

  // placeMemo 75xxx
  NOT_FOUND_PLACETAG(HttpStatus.NOT_FOUND, "75000", "존재하지 않는 태그 입니다"),
  NOT_FOUND_PLACE_MEMO(HttpStatus.NOT_FOUND, "75001", "존재하지 않는 PlaceMemo 입니다"),
  INVALID_IMAGE_COUNT(HttpStatus.BAD_REQUEST, "75002", "주변장소 메모는최대 5개의 이미지를 첨부할 있습니다]"),

  // property record 74xxx
  PROPERTY_RECORD_IMAGE_LIMIT(HttpStatus.BAD_REQUEST, "74000", "매물 메모는 최대 5개의 이미지를 첨부할 수 있습니다"),
  NOT_FOUND_PROPERTY(HttpStatus.NOT_FOUND, "74001", "존재하지 않는 매물입니다"),
  NOT_FOUND_PROPERTY_IMAGE(HttpStatus.NOT_FOUND, "74002", "해당 매물 메모에 속해있지 않은 이미지가 요청되었습니다"),
  PROPERTY_CREATION_LIMIT(HttpStatus.BAD_REQUEST, "74003", "한 폴더당 최대 생성할 수 있는 메모는 10개 입니다"),

  // s3 manager 에러 80000
  INVALID_FILE_URL(HttpStatus.BAD_REQUEST, "80000", "유효하지 않은 파일 URL입니다"),
  FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "80001", "파일을 찾을 수 없습니다"),
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
