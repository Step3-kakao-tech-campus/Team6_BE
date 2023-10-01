package com.example.tripKo.domain.place.dto;

import com.example.tripKo.domain.address.entity.Address;
import com.example.tripKo.domain.address.entity.AddressCategory;
import com.example.tripKo.domain.place.entity.PlaceFestival;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PlaceFestivalResponseDTO {
    private Long id;
    private String name;
    private String summary;
    private String image;
    private String address;
    private float averageRating;
    private Boolean liked;
    @Builder
    public PlaceFestivalResponseDTO(PlaceFestival placeFestival) {
        this.id = placeFestival.getId();
        this.name = placeFestival.getPlace().getName();
        this.summary = placeFestival.getPlace().getSummary();
        this.image = placeFestival.getPlace().getFile().getName();
        this.address = addressToString(placeFestival.getPlace().getAddress());
        this.averageRating = placeFestival.getPlace().getAverageRating();
        this.liked = false;
    }

    public String addressToString(Address address) {
        String addressToString = address.getBuildingName() + " " + address.getRoadName();
        AddressCategory addressCategory = address.getAddressCategory();
        String addressCategoryToString = addressCategory.getEmdName() + " " + addressCategory.getSiggName() + " " + addressCategory.getSidoName();
        return addressToString + " " + addressCategoryToString;
    }

}
