package com.example.tripKo.domain.member.dto.request;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = PACKAGE)
@NoArgsConstructor(access = PRIVATE)
public class SignInRequest {

  private String memberId;
  private String password;

}
