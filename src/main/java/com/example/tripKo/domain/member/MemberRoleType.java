package com.example.tripKo.domain.member;

import lombok.Getter;

public enum MemberRoleType {
  비회원(1),
  정회원(2),
  휴면회원(3);

  @Getter
  private final long id;

  MemberRoleType(long id) {
    this.id = id;
  }
}
