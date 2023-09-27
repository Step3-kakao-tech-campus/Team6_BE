package com.example.tripKo.domain.place.festival;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PlaceFestivalResponseDTO {
    private int id;
    private String name;
    private String summary;
    private String image;
    private String address;
    private float averageRating;
//    private Boolean liked;
    @Builder
    public PlaceFestivalResponseDTO(PlaceFestival placeFestival) {
        this.id = placeFestival.getId();
        this.name = placeFestival.getPlace().getName();
        this.summary = placeFestival.getPlace().getSummary();
//        this.image = placeFestival.getPlace().getFile().getName();
        this.address = placeFestival.getPlace().getAddress().toString();
        this.averageRating = placeFestival.getPlace().getAverageRating();
    }
}
