package com.example.tripKo._core.security.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Id;

@AllArgsConstructor
@Getter
public class RefreshToken {

  // memberId
  @Id
  private String id;

  private String refreshToken;
}
