package com.example.tripKo._core.security.data;

import lombok.Getter;

@Getter
public enum JwtType {
  ACCESS_TOKEN("accessToken", 30 * 60 * 1000L),
  REFRESH_TOKEN("refreshToken", 60 * 24 * 3 * 60 * 1000L);

  private final String tokenName;
  private final long expiredMillis;

  JwtType(String tokenName, long expiredMillis) {
    this.tokenName = tokenName;
    this.expiredMillis = expiredMillis;
  }
}
