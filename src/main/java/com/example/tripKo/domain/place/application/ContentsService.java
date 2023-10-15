package com.example.tripKo.domain.place.application;

import com.example.tripKo.domain.place.application.convenience.ValidContentsService;
import com.example.tripKo.domain.place.dto.response.info.FestivalResponse;
import com.example.tripKo.domain.place.dto.response.info.TouristSpotResponse;
import com.example.tripKo.domain.place.dto.response.info.RestaurantResponse;
import com.example.tripKo.domain.place.entity.PlaceFestival;
import com.example.tripKo.domain.place.entity.PlaceRestaurant;
import com.example.tripKo.domain.place.entity.PlaceTouristSpot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentsService {

  private final ValidContentsService validContentsService;

  @Transactional
  public RestaurantResponse findRestaurantDetailsById(Long restaurantId) {
    PlaceRestaurant placeRestaurant = validContentsService.findByRestaurantId(restaurantId);
    return RestaurantResponse.from(placeRestaurant);
  }

  @Transactional
  public FestivalResponse getFestivalInfo(Long festivalId) {
    PlaceFestival placeFestival = validContentsService.findByFestivalId(festivalId);
    return FestivalResponse.from(placeFestival);
  }

  @Transactional
  public TouristSpotResponse getTouristSpotInfo(Long touristSpotId) {
    PlaceTouristSpot placeTouristSpot = validContentsService.findByTouristSpotId(touristSpotId);
    return TouristSpotResponse.from(placeTouristSpot);
  }
}
