package com.example.tripKo.place.touristSpot;

import com.example.tripKo.place.restaurant.PlaceRestaurant;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PlaceTouristSpotResponseDTO {
    private int id;
    private String name;
    private String summary;
    //    private String image;
    private String address;
    private float averageRating;
    //    private Boolean liked;
    @Builder
    public PlaceTouristSpotResponseDTO(PlaceTouristSpot placeTouristSpot) {
        this.id = placeTouristSpot.getId();
        this.name = placeTouristSpot.getPlace().getName();
        this.summary = placeTouristSpot.getPlace().getSummary();
//        this.image = placeTouristSpot.getPlace().getFile();
        this.address = placeTouristSpot.getPlace().getAddress();
        this.averageRating = placeTouristSpot.getPlace().getAverageRating();
    }
}
