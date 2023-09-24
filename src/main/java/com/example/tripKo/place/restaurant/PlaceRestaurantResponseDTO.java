package com.example.tripKo.place.restaurant;

import com.example.tripKo.place.festival.PlaceFestival;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PlaceRestaurantResponseDTO {
    private int id;
    private String name;
    private String summary;
    //    private String image;
    private String address;
    private float averageRating;
    //    private Boolean liked;
    @Builder
    public PlaceRestaurantResponseDTO(PlaceRestaurant placeRestaurant) {
        this.id = placeRestaurant.getId();
        this.name = placeRestaurant.getPlace().getName();
        this.summary = placeRestaurant.getPlace().getSummary();
//        this.image = placeRestaurant.getPlace().getFile();
        this.address = placeRestaurant.getPlace().getAddress();
        this.averageRating = placeRestaurant.getPlace().getAverageRating();
    }
}
