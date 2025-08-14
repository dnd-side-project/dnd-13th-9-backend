package com.example.dnd_13th_9_be.global.error;

public class UserNotFoundException extends BusinessException {

  public UserNotFoundException() {
    super(ErrorCode.USER_NOT_FOUND);
  }

  public UserNotFoundException(String message) {
    super(ErrorCode.USER_NOT_FOUND, message);
  }

  public UserNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }

  public UserNotFoundException(ErrorCode errorCode, String message) {
    super(errorCode, message);
  }
}
