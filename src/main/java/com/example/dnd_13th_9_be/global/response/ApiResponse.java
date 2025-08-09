package com.example.dnd_13th_9_be.global.response;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import lombok.Getter;

import com.example.dnd_13th_9_be.global.error.ErrorCode;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Getter
@JsonPropertyOrder({"code", "message", "data"})
public class ApiResponse<T> {
  private final String code;
  private final String message;
  private final T data;

  private ApiResponse(String code, String message, T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public static ApiResponse<Map<String, Object>> ok() {
    return success(Map.of());
  }

  public static <T> ApiResponse<T> success(T data) {
    return of(SuccessCode.SUCCESS, data);
  }

  public static <T> ApiResponse<T> error(ErrorCode code) {
    return of(code, null);
  }

  public static <T> ApiResponse<T> of(ResponseCode code, T data) {
    return new ApiResponse<>(code.code(), code.defaultMessage(), data);
  }

  public static ResponseEntity<ApiResponse<Map<String, Object>>> okEntity() {
    return ResponseEntity.ok(ok());
  }

  public static <T> ResponseEntity<ApiResponse<T>> successEntity(T data) {
    return ResponseEntity.status(SuccessCode.SUCCESS.httpStatus()).body(ApiResponse.success(data));
  }

  public static <T> ResponseEntity<ApiResponse<T>> errorEntity(ErrorCode code) {
    return ResponseEntity.status(code.httpStatus()).body(error(code));
  }

  public static <T> ResponseEntity<ApiResponse<T>> entity(ResponseCode code, T data) {
    return ResponseEntity.status(code.httpStatus()).body(ApiResponse.of(code, data));
  }
}
