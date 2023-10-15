package com.example.tripKo.domain.place.application.convenience;

import com.example.tripKo._core.errors.exception.Exception404;
import com.example.tripKo.domain.place.dao.PlaceFestivalRepository;
import com.example.tripKo.domain.place.dao.PlaceRestaurantRepository;
import com.example.tripKo.domain.place.dao.PlaceTouristSpotRepository;
import com.example.tripKo.domain.place.entity.PlaceFestival;
import com.example.tripKo.domain.place.entity.PlaceRestaurant;
import com.example.tripKo.domain.place.entity.PlaceTouristSpot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ValidContentsService {

  private static final long VIRTUAL_FESTIVAL_ID = 1;
  private static final long VIRTUAL_RESTAURANT_ID = 1;
  private static final long VIRTUAL_TOURISTSPOT_ID = 1;

  private final PlaceRestaurantRepository placeRestaurantRepository;
  private final PlaceFestivalRepository placeFestivalRepository;
  private final PlaceTouristSpotRepository placeTouristSpotRepository;

  public PlaceRestaurant findByRestaurantId(long restaurantId) {
    return placeRestaurantRepository.findByIdAndIdNot(restaurantId, VIRTUAL_RESTAURANT_ID)
        .orElseThrow(() -> new Exception404("해당 식당을 찾을 수 없습니다. id : " + restaurantId));
  }

  public PlaceFestival findByFestivalId(long festivalId) {
    return placeFestivalRepository.findByIdAndIdNot(festivalId, VIRTUAL_FESTIVAL_ID)
        .orElseThrow(() -> new Exception404("해당 축제를 찾을 수 없습니다. id: " + festivalId));
  }

  public PlaceTouristSpot findByTouristSpotId(long touristSpotId) {
    return placeTouristSpotRepository.findByIdAndIdNot(touristSpotId, VIRTUAL_TOURISTSPOT_ID)
        .orElseThrow(() -> new Exception404("해당 명소를 찾을 수 없습니다. id: " + touristSpotId));
  }
}
