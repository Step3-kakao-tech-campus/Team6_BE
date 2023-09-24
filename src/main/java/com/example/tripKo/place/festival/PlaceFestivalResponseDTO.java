package com.example.tripKo.place.festival;

import com.example.tripKo.place.Category;
import com.example.tripKo.place.Place;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

@Getter
public class PlaceFestivalResponseDTO {
    private int id;
    private String name;
    private String summary;
//    private String image;
    private String address;
    private float averageRating;
//    private Boolean liked;
    @Builder
    public PlaceFestivalResponseDTO(PlaceFestival placeFestival) {
        this.id = placeFestival.getId();
        this.name = placeFestival.getPlace().getName();
        this.summary = placeFestival.getPlace().getSummary();
//        this.image = placeFestival.getPlace().getFile();
        this.address = placeFestival.getPlace().getAddress();
        this.averageRating = placeFestival.getPlace().getAverageRating();
    }
}
