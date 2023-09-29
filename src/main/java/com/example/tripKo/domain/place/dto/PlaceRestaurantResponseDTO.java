package com.example.tripKo.domain.place.dto;

import com.example.tripKo.domain.address.entity.Address;
import com.example.tripKo.domain.address.entity.AddressCategory;
import com.example.tripKo.domain.place.entity.PlaceRestaurant;
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
    private Boolean liked;
    @Builder
    public PlaceRestaurantResponseDTO(PlaceRestaurant placeRestaurant) {
        this.id = placeRestaurant.getId();
        this.name = placeRestaurant.getPlace().getName();
        this.summary = placeRestaurant.getPlace().getSummary();
        this.image = placeRestaurant.getPlace().getFile().getName();;
        this.address = addressToString(placeRestaurant.getPlace().getAddress());
        this.averageRating = placeRestaurant.getPlace().getAverageRating();
        this.liked = false;
    }
    public String addressToString(Address address) {
        String addressToString = address.getBuildingName() + " " + address.getRoadName();
        AddressCategory addressCategory = address.getAddressCategory();
        String addressCategoryToString = addressCategory.getEmdName() + " " + addressCategory.getSiggName() + " " + addressCategory.getSidoName();
        return addressToString + " " + addressCategoryToString;
    }
}
