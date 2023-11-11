package com.example.tripKo.domain.place;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

  BEGAN("BEGAN", "채식주의"),
  HALAL("HALAL", "할랄");

  private final String key;
  private final String title;

}
