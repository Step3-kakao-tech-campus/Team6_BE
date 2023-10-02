package com.example.tripKo.domain.contents.application;

import com.example.tripKo._core.errors.exception.Exception404;
import com.example.tripKo.domain.contents.dto.response.FestivalResponse;
import com.example.tripKo.domain.contents.dto.response.TouristSpotResponse;
import com.example.tripKo.domain.place.dao.PlaceFestivalJPARepository;
import com.example.tripKo.domain.place.dao.PlaceRestaurantJPARepository;
import com.example.tripKo.domain.place.dao.PlaceTouristSpotJPARepository;
import com.example.tripKo.domain.contents.dto.response.RestaurantResponse;
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

  private final PlaceRestaurantJPARepository placeRestaurantJPARepository;
  private final PlaceFestivalJPARepository placeFestivalJPARepository;
  private final PlaceTouristSpotJPARepository placeTouristSpotJPARepository;

  @Transactional
  public RestaurantResponse findRestaurantDetailsById(Long id) {
        PlaceRestaurant placeRestaurant = placeRestaurantJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("해당하는 식당을 찾을 수 없습니다. id : " + id));
        RestaurantResponse ResponseDTO = new RestaurantResponse(placeRestaurant);
        return ResponseDTO;
  }

  @Transactional
  public FestivalResponse getFestivalInfo(Long id) {
    PlaceFestival placeFestival = placeFestivalJPARepository.findById(id)
        .orElseThrow(() -> new Exception404("해당하는 컨텐츠를 찾을 수 없습니다. id: " + id));
    FestivalResponse festivalResponse = new FestivalResponse(placeFestival);
    return festivalResponse;
  }

  @Transactional
  public TouristSpotResponse getTouristSpotInfo(Long id) {
    PlaceTouristSpot placeTouristSpot = placeTouristSpotJPARepository.findById(id)
            .orElseThrow(() -> new Exception404("해당하는 컨텐츠를 찾을 수 없습니다. id: " + id));
    TouristSpotResponse touristSpotResponse = new TouristSpotResponse(placeTouristSpot);
    return touristSpotResponse;
  }
}
