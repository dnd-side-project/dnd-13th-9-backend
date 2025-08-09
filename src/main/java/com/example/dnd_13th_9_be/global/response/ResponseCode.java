package com.example.dnd_13th_9_be.global.response;

import org.springframework.http.HttpStatus;

public interface ResponseCode {
  HttpStatus httpStatus();

  String code();

  String defaultMessage();
}
