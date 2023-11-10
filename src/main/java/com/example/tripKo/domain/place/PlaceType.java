package com.example.tripKo.domain.place;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PlaceType {
  RESTAURANT(1),
  FESTIVAL(2),
  TOURIST_SPOT(3);


  private final long id;
}
