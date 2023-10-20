package com.example.tripKo.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRoleType {
  비회원(1, "ROLE_ANONYMOUS"),
  정회원(2, "ROLE_MEMBER"),
  휴면회원(3, "ROLE_DORMANT");


  private final long id;
  private final String key;
}
