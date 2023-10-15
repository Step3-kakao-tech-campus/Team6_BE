package com.example.tripKo.domain.place.application.convenience;

import com.example.tripKo._core.errors.exception.Exception404;
import com.example.tripKo.domain.place.dao.ContentsRepository;
import com.example.tripKo.domain.place.dao.PlaceFestivalRepository;
import com.example.tripKo.domain.place.dao.PlaceRestaurantRepository;
import com.example.tripKo.domain.place.dao.PlaceTouristSpotRepository;
import com.example.tripKo.domain.place.entity.PlaceFestival;
import com.example.tripKo.domain.place.entity.PlaceRestaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ValidContentsService {

  private static final long VIRTUAL_CONTENTS_ID = 1;

  private final ContentsRepository contentsRepository;
  private final PlaceRestaurantRepository placeRestaurantRepository;
  private final PlaceFestivalRepository placeFestivalRepository;
  private final PlaceTouristSpotRepository placeTouristSpotRepository;

  public PlaceFestival findByFestivalId(long festivalId) {
    return placeFestivalRepository.findByIdAndIdNot(festivalId, VIRTUAL_CONTENTS_ID)
        .orElseThrow(() -> new Exception404("해당 축제를 찾을 수 없습니다. id: " + festivalId));
  }
}
