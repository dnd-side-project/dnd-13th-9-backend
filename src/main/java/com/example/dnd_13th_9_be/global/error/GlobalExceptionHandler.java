package com.example.dnd_13th_9_be.global.error;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

import com.example.dnd_13th_9_be.global.response.ApiResponse;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import io.swagger.v3.oas.annotations.Hidden;

@Slf4j
@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiResponse<Map<String, Object>>> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex) {
    log.warn("HttpMessageNotReadableException: {}", ex.getMessage(), ex);

    Map<String, Object> body = new LinkedHashMap<>();

    Throwable root = getRootCause(ex);
    // 1) 본문 자체가 비었을 가능성(대개 이 메시지로 올라옴)
    if (ex.getMessage() != null
        && ex.getMessage().toLowerCase().contains("required request body is missing")) {
      body.put("reason", "요청 본문이 비어 있습니다");
      body.put("hint", "Content-Type: application/json 으로 JSON 본문을 보내주세요");
      return ApiResponse.entity(ErrorCode.BAD_REQUEST, body);
    }

    // 2) JSON 문법 오류
    if (root instanceof JsonParseException jpe) {
      body.put("reason", "JSON 문법 오류");
      body.put(
          "location",
          "line " + jpe.getLocation().getLineNr() + ", column " + jpe.getLocation().getColumnNr());
      body.put("hint", "JSON 형식을 다시 확인해주세요(따옴표/쉼표/브레이스 등)");
      return ApiResponse.entity(ErrorCode.VALIDATION_ERROR, body);
    }

    // 3) JSON 타입 매핑 오류(필드 타입 불일치, 누락 등)
    if (root instanceof MismatchedInputException mie) {
      body.put("reason", "요청 필드 타입이 맞지 않습니다");
      body.put("path", jacksonPath(mie.getPath())); // 오류난 경로
      if (mie.getTargetType() != null) {
        body.put("expectedType", "요청 스키마와 타입을 확인하세요");
      }
      body.put("hint", mie.getOriginalMessage());
      return ApiResponse.entity(ErrorCode.VALIDATION_ERROR, body);
    }

    // 4) 그 외 읽기 불가(일반 케이스)
    body.put("reason", "요청 본문을 읽을 수 없습니다");
    body.put("hint", "JSON 형식 및 Content-Type을 확인하세요");
    return ApiResponse.entity(ErrorCode.BAD_REQUEST, body);
  }

  private static Throwable getRootCause(Throwable t) {
    Throwable result = t;
    while (result.getCause() != null && result.getCause() != result) {
      result = result.getCause();
    }
    return result;
  }

  private static String jacksonPath(List<Reference> path) {
    if (path == null || path.isEmpty()) return "";
    return path.stream()
        .map(ref -> ref.getFieldName() != null ? ref.getFieldName() : "[" + ref.getIndex() + "]")
        .collect(Collectors.joining("."));
  }

  @ExceptionHandler(BindException.class)
  public ResponseEntity<ApiResponse<Map<String, String>>> handleBind(BindException ex) {
    log.warn(ex.getMessage(), ex);
    Map<String, String> fieldErrors = new LinkedHashMap<>();
    ex.getFieldErrors().forEach(fe -> fieldErrors.put(fe.getField(), fe.getDefaultMessage()));
    return ApiResponse.entity(ErrorCode.VALIDATION_ERROR, fieldErrors);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ApiResponse<Map<String, String>>> handleConstraintViolation(
      ConstraintViolationException ex) {
    log.warn(ex.getMessage(), ex);
    Map<String, String> fieldErrors = new LinkedHashMap<>();
    for (ConstraintViolation<?> v : ex.getConstraintViolations()) {
      fieldErrors.put(
          v.getPropertyPath().toString(), v.getMessage() != null ? v.getMessage() : "invalid");
    }
    return ApiResponse.entity(ErrorCode.VALIDATION_ERROR, fieldErrors);
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ApiResponse<Void>> handleBusiness(BusinessException ex) {
    // BusinessException의 message는 디버깅을 위한 로깅으로 사용되며
    // 클라이언트는 message를 보는 것이 아닌 ErrorCode의 message를 전달 받습니다
    log.warn(ex.getMessage(), ex);
    return ApiResponse.errorEntity(ex.getErrorCode());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleGeneric(Exception ex) {
    log.error(ex.getMessage(), ex);
    return ApiResponse.errorEntity(ErrorCode.INTERNAL_ERROR);
  }
}
