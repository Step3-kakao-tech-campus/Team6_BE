package com.example.tripKo.domain.place.restaurant;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PlaceRestaurantResponseDTO {
    private int id;
    private String name;
    private String summary;
    private String image;
    private String address;
    private float averageRating;
    //    private Boolean liked;
    @Builder
    public PlaceRestaurantResponseDTO(PlaceRestaurant placeRestaurant) {
        this.id = placeRestaurant.getId();
        this.name = placeRestaurant.getPlace().getName();
        this.summary = placeRestaurant.getPlace().getSummary();
//        this.image = placeRestaurant.getPlace().getFile().getName();;
        this.address = placeRestaurant.getPlace().getAddress().toString();
        this.averageRating = placeRestaurant.getPlace().getAverageRating();
    }
}
