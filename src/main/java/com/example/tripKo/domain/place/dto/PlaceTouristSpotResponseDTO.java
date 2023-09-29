package com.example.tripKo.domain.place.dto;

import com.example.tripKo.domain.address.entity.Address;
import com.example.tripKo.domain.address.entity.AddressCategory;
import com.example.tripKo.domain.place.entity.PlaceTouristSpot;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PlaceTouristSpotResponseDTO {
    private int id;
    private String name;
    private String summary;
    private String image;
    private String address;
    private float averageRating;
    private Boolean liked;
    @Builder
    public PlaceTouristSpotResponseDTO(PlaceTouristSpot placeTouristSpot) {
        this.id = placeTouristSpot.getId();
        this.name = placeTouristSpot.getPlace().getName();
        this.summary = placeTouristSpot.getPlace().getSummary();
        this.image = placeTouristSpot.getPlace().getFile().getName();
        this.address = addressToString(placeTouristSpot.getPlace().getAddress());
        this.averageRating = placeTouristSpot.getPlace().getAverageRating();
        this.liked = false;
    }

    public String addressToString(Address address) {
        String addressToString = address.getBuildingName() + " " + address.getRoadName();
        AddressCategory addressCategory = address.getAddressCategory();
        String addressCategoryToString = addressCategory.getEmdName() + " " + addressCategory.getSiggName() + " " + addressCategory.getSidoName();
        return addressToString + " " + addressCategoryToString;
    }
}
