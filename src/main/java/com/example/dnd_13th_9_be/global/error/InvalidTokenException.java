package com.example.dnd_13th_9_be.global.error;

public class InvalidTokenException extends BusinessException {

  public InvalidTokenException() {
    super(ErrorCode.TOKEN_NOT_FOUND);
  }

  public InvalidTokenException(String message) {
    super(ErrorCode.TOKEN_NOT_FOUND, message);
  }

  public InvalidTokenException(ErrorCode errorCode) {
    super(errorCode);
  }

  public InvalidTokenException(ErrorCode errorCode, String message) {
    super(errorCode, message);
  }
}
