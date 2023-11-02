package com.example.tripKo._core.security.data;

import lombok.Getter;

@Getter
public enum JwtValidationType {
  VALID("유효한 토큰입니다."),
  MALFORMED("손상된 토큰입니다."),
  EXPIRED("만료된 토큰입니다."),
  UNSUPPORTED("손상된 토큰입니다."),
  EMPTY("빈 토큰입니다");

  private final String message;

  JwtValidationType(String message) {
    this.message = message;
  }
}
