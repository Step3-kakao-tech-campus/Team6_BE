package com.example.tripKo._core.errors.exception;

import com.example.tripKo._core.errors.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

  private final String invalidValue;
  private final String fieldName;
  private final HttpStatus httpStatus;
  private final String message;

  public BusinessException(Object invalidValue, String fieldName, ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.invalidValue = invalidValue != null ? invalidValue.toString() : null;
    this.fieldName = fieldName;
    this.httpStatus = errorCode.getHttpStatus();
    this.message = errorCode.getMessage();
  }
}
