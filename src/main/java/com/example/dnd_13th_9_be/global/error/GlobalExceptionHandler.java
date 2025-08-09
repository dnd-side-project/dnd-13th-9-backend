package com.example.dnd_13th_9_be.global.error;

import java.util.LinkedHashMap;
import java.util.Map;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

import com.example.dnd_13th_9_be.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Hidden;

@Slf4j
@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {
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
