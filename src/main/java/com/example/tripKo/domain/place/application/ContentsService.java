package com.example.tripKo.domain.place.application;

import com.example.tripKo._core.errors.exception.Exception404;
import com.example.tripKo.domain.place.application.convenience.ValidContentsService;
import com.example.tripKo.domain.place.dto.response.info.FestivalResponse;
import com.example.tripKo.domain.place.dto.response.info.TouristSpotResponse;
import com.example.tripKo.domain.place.dao.PlaceFestivalRepository;
import com.example.tripKo.domain.place.dao.PlaceRestaurantRepository;
import com.example.tripKo.domain.place.dao.PlaceTouristSpotRepository;
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

  private final PlaceRestaurantRepository placeRestaurantRepository;
  private final PlaceFestivalRepository placeFestivalRepository;
  private final PlaceTouristSpotRepository placeTouristSpotRepository;

  private final ValidContentsService validContentsService;

  @Transactional
  public RestaurantResponse findRestaurantDetailsById(Long id) {
    PlaceRestaurant placeRestaurant = placeRestaurantRepository.findById(id)
        .orElseThrow(() -> new Exception404("해당하는 식당을 찾을 수 없습니다. id : " + id));
    RestaurantResponse ResponseDTO = new RestaurantResponse(placeRestaurant);
    return ResponseDTO;
  }

  @Transactional
  public FestivalResponse getFestivalInfo(Long festivalId) {
    PlaceFestival placeFestival = validContentsService.findByFestivalId(festivalId);
    return FestivalResponse.from(placeFestival);
  }

  @Transactional
  public TouristSpotResponse getTouristSpotInfo(Long id) {
    PlaceTouristSpot placeTouristSpot = placeTouristSpotRepository.findById(id)
        .orElseThrow(() -> new Exception404("해당하는 컨텐츠를 찾을 수 없습니다. id: " + id));
    TouristSpotResponse touristSpotResponse = new TouristSpotResponse(placeTouristSpot);
    return touristSpotResponse;
  }
}
