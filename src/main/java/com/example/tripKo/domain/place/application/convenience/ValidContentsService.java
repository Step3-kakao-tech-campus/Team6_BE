package com.example.tripKo.domain.place.application.convenience;

import static com.example.tripKo._core.errors.ErrorCode.FESTIVAL_ID_CANNOT_FOUND;
import static com.example.tripKo._core.errors.ErrorCode.RESTAURANT_ID_CANNOT_FOUND;
import static com.example.tripKo._core.errors.ErrorCode.TOURISTSPOT_ID_CANNOT_FOUND;

import com.example.tripKo._core.errors.ErrorCode;
import com.example.tripKo._core.errors.exception.BusinessException;
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

  private final PlaceRestaurantRepository placeRestaurantRepository;
  private final PlaceFestivalRepository placeFestivalRepository;
  private final PlaceTouristSpotRepository placeTouristSpotRepository;

  public PlaceRestaurant findByRestaurantId(long placeId) {
    return placeRestaurantRepository.findByPlaceId(placeId)
        .orElseThrow(() -> new BusinessException(placeId, "placeId", RESTAURANT_ID_CANNOT_FOUND));
  }

  public PlaceFestival findByFestivalId(long placeId) {
    return placeFestivalRepository.findByPlaceId(placeId)
        .orElseThrow(() -> new BusinessException(placeId, "placeId", FESTIVAL_ID_CANNOT_FOUND));
  }

  public PlaceTouristSpot findByTouristSpotId(long placeId) {
    return placeTouristSpotRepository.findByPlaceId(placeId)
        .orElseThrow(() -> new BusinessException(placeId, "placeId", TOURISTSPOT_ID_CANNOT_FOUND));
  }
}
