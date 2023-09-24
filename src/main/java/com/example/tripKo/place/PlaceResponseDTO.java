package com.example.tripKo.place;

import com.example.tripKo.place.festival.PlaceFestival;
import com.example.tripKo.place.festival.PlaceFestivalResponseDTO;
import com.example.tripKo.place.restaurant.PlaceRestaurant;
import com.example.tripKo.place.restaurant.PlaceRestaurantResponseDTO;
import com.example.tripKo.place.touristSpot.PlaceTouristSpot;
import com.example.tripKo.place.touristSpot.PlaceTouristSpotResponseDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
@Getter
public class PlaceResponseDTO {
    private List<PlaceRestaurantResponseDTO> restaurants;
    private List<PlaceFestivalResponseDTO> festivals;
    private List<PlaceTouristSpotResponseDTO> touristSpots;

    @Builder
    public PlaceResponseDTO(List<PlaceRestaurant> placeRestaurants, List<PlaceFestival> placeFestivals, List<PlaceTouristSpot> placeTouristSpots) {
        this.restaurants = placeRestaurants.stream().map(p->PlaceRestaurantResponseDTO.builder().placeRestaurant(p).build()).collect(Collectors.toList());
        this.festivals = placeFestivals.stream().map(p->PlaceFestivalResponseDTO.builder().placeFestival(p).build()).collect(Collectors.toList());
        this.touristSpots = placeTouristSpots.stream().map(p->PlaceTouristSpotResponseDTO.builder().placeTouristSpot(p).build()).collect(Collectors.toList());
    }
}
