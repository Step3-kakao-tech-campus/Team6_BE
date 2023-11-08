package com.example.tripKo.domain.place.application;

import com.example.tripKo.domain.place.application.convenience.ValidContentsService;
import com.example.tripKo.domain.place.dao.PlaceFestivalRepository;
import com.example.tripKo.domain.place.dao.PlaceRestaurantRepository;
import com.example.tripKo.domain.place.dao.PlaceTouristSpotRepository;
import com.example.tripKo.domain.place.dto.response.info.FestivalResponse;
import com.example.tripKo.domain.place.dto.response.info.HomeResponse;
import com.example.tripKo.domain.place.dto.response.info.TouristSpotResponse;
import com.example.tripKo.domain.place.dto.response.info.RestaurantResponse;
import com.example.tripKo.domain.place.entity.PlaceFestival;
import com.example.tripKo.domain.place.entity.PlaceRestaurant;
import com.example.tripKo.domain.place.entity.PlaceTouristSpot;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentsService {

  private final ValidContentsService validContentsService;
  private final PlaceRestaurantRepository placeRestaurantRepository;
  private final PlaceFestivalRepository placeFestivalRepository;
  private final PlaceTouristSpotRepository placeTouristSpotRepository;

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

  @Transactional
  public HomeResponse getHomeInfo() {
    List<PlaceRestaurant> placeRestaurants = placeRestaurantRepository.findAll();
    List<RestaurantResponse> restaurantResponses = placeRestaurants.stream()
        .map(RestaurantResponse::from)
        .collect(Collectors.toList());

    List<PlaceFestival> placeFestivals = placeFestivalRepository.findAll();
    List<FestivalResponse> festivalResponses = placeFestivals.stream()
        .map(FestivalResponse::from)
        .collect(Collectors.toList());

    List<PlaceTouristSpot> placeTouristSpots = placeTouristSpotRepository.findAll();
    List<TouristSpotResponse> touristSpotResponses = placeTouristSpots.stream()
        .map(TouristSpotResponse::from)
        .collect(Collectors.toList());

    return HomeResponse.from(restaurantResponses, festivalResponses, touristSpotResponses);
  }

}
