package com.example.tripKo.domain.place.dto.response.search;

import com.example.tripKo.domain.place.entity.Place;
import com.example.tripKo.domain.place.entity.PlaceFestival;
import com.example.tripKo.domain.place.entity.PlaceRestaurant;
import com.example.tripKo.domain.place.entity.PlaceTouristSpot;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PlaceResponse {

  private List<PlaceRestaurantResponse> restaurants;
  private List<PlaceFestivalResponse> festivals;
  private List<PlaceTouristSpotResponse> touristSpots;

  @Builder
  public PlaceResponse(List<PlaceRestaurant> placeRestaurants, List<PlaceFestival> placeFestivals,
                       List<PlaceTouristSpot> placeTouristSpots, List<Place> places) {
    this.restaurants = placeRestaurants.stream().map(p -> PlaceRestaurantResponse.builder().placeRestaurant(p).isWished(places.contains(p.getPlace())).build())
        .collect(Collectors.toList());
    this.festivals = placeFestivals.stream().map(p -> PlaceFestivalResponse.builder().placeFestival(p).isWished(places.contains(p.getPlace())).build())
        .collect(Collectors.toList());
    this.touristSpots = placeTouristSpots.stream()
        .map(p -> PlaceTouristSpotResponse.builder().placeTouristSpot(p).isWished(places.contains(p.getPlace())).build()).collect(Collectors.toList());
  }
}
