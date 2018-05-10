package com.use.exception;

/**
 * Created by julius on 04/05/2018.
 */
public class EncryptException extends RuntimeException {

  private ErrorCode errorCode;

  public EncryptException(ErrorCode errorCode) {
    this.errorCode = errorCode;
  }

  public EncryptException(String message, ErrorCode errorCode) {
    super(message);
    this.errorCode = errorCode;
  }

  public EncryptException(String message, Throwable cause, ErrorCode errorCode) {
    super(message, cause);
    this.errorCode = errorCode;
  }

  public EncryptException(Throwable cause, ErrorCode errorCode) {
    super(cause);
    this.errorCode = errorCode;
  }

}
