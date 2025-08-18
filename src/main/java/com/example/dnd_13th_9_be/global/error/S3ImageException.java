package com.example.dnd_13th_9_be.global.error;

public class S3ImageException extends BusinessException {
    public S3ImageException() {
        super(ErrorCode.TOKEN_NOT_FOUND);
    }

    public S3ImageException(String message) {
        super(ErrorCode.TOKEN_NOT_FOUND, message);
    }

    public S3ImageException(ErrorCode errorCode) {
        super(errorCode);
    }

    public S3ImageException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
