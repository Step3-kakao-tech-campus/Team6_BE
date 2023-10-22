package com.example.tripKo._core.security.data;

import lombok.Getter;

@Getter
public class TokenValidationDTO {

  private final boolean isValid;
  private final JwtValidationType jwtValidationType;
  private final String token;

  private TokenValidationDTO(boolean isValid, JwtValidationType jwtValidationType, String token) {
    this.isValid = isValid;
    this.jwtValidationType = jwtValidationType;
    this.token = token;
  }

  public static TokenValidationDTO of(boolean isValid, JwtValidationType jwtValidationType, String token) {
    return new TokenValidationDTO(isValid, jwtValidationType, token);
  }

  public static TokenValidationDTO of(boolean isValid, JwtValidationType jwtValidationType) {
    return new TokenValidationDTO(isValid, jwtValidationType, null);
  }

}
