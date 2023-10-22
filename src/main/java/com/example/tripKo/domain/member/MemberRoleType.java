package com.example.tripKo.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRoleType {
  ANONYMOUS(1, "ROLE_ANONYMOUS"),
  MEMBER(2, "ROLE_MEMBER"),
  DORMANT(3, "ROLE_DORMANT");

  private final long id;
  private final String key;
}
