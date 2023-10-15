package com.example.tripKo.domain.member;

import lombok.Getter;

public enum MemberReservationStatus {
  예약대기(1),
  예약완료(2);

  @Getter
  private final long id;

  MemberReservationStatus(long id) {
    this.id = id;
  }
}
