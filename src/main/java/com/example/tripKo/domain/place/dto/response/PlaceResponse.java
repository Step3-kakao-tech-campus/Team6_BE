package com.example.tripKo.domain.place.dto.response;

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
    public PlaceResponse(List<PlaceRestaurant> placeRestaurants, List<PlaceFestival> placeFestivals, List<PlaceTouristSpot> placeTouristSpots) {
        this.restaurants = placeRestaurants.stream().map(p-> PlaceRestaurantResponse.builder().placeRestaurant(p).build()).collect(Collectors.toList());
        this.festivals = placeFestivals.stream().map(p-> PlaceFestivalResponse.builder().placeFestival(p).build()).collect(Collectors.toList());
        this.touristSpots = placeTouristSpots.stream().map(p-> PlaceTouristSpotResponse.builder().placeTouristSpot(p).build()).collect(Collectors.toList());
    }
}
