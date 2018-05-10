package com.use.exception;

/**
 * Created by julius on 04/05/2018.
 */
public enum ErrorCode {

  ENCRYPTION_ERROR(9001, "Encryption error occurred"),
  DECRYPTION_ERROR(9002, "Encryption error occurred")
  ;

  private int code;
  private String message;

  ErrorCode(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
