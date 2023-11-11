package com.example.tripKo.domain.place.dto.request;

import com.example.tripKo.domain.place.PlaceType;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PACKAGE)
@NoArgsConstructor(access = PRIVATE)
public class FestivalReservationConfirmRequest {

  private Reservation reservation;

  @Getter
  @Builder
  public static class Reservation {
    @NotNull
    private Long placeId;
    @NotNull
    private String reservationDate;
    @NotNull
    private Long headCount;
    private String message;
  }
}
