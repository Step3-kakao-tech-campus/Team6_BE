package com.example.tripKo.domain.place.dto.response.search;

import com.example.tripKo.domain.place.entity.Address;
import com.example.tripKo.domain.place.entity.AddressCategory;
import com.example.tripKo.domain.place.entity.PlaceTouristSpot;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PlaceTouristSpotResponse {

  private Long id;
  private String name;
  private String summary;
  private String image;
  private String address;
  private float averageRating;
  private Boolean isWished;

  @Builder
  public PlaceTouristSpotResponse(PlaceTouristSpot placeTouristSpot) {
    this.id = placeTouristSpot.getPlace().getId();
    this.name = placeTouristSpot.getPlace().getName();
    this.summary = placeTouristSpot.getPlace().getSummary();
    this.image = placeTouristSpot.getPlace().getFile().getName();
    this.address = addressToString(placeTouristSpot.getPlace().getAddress());
    this.averageRating = placeTouristSpot.getPlace().getAverageRating();
    this.isWished = false;
  }

  public String addressToString(Address address) {
    String addressToString = address.getBuildingName() + " " + address.getRoadName();
    AddressCategory addressCategory = address.getAddressCategory();
    String addressCategoryToString =
        addressCategory.getEmdName() + " " + addressCategory.getSiggName() + " " + addressCategory.getSidoName();
    return addressToString + " " + addressCategoryToString;
  }
}
