package com.example.tripKo.domain.place.dto.response.search;

import com.example.tripKo.domain.member.entity.MemberHasPlaceIsWished;
import com.example.tripKo.domain.place.entity.Address;
import com.example.tripKo.domain.place.entity.AddressCategory;
import com.example.tripKo.domain.place.entity.PlaceRestaurant;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PlaceRestaurantResponse {

  private long id;
  private String name;
  private String summary;
  private String image;
  private String address;
  private float averageRating;
  private Boolean isWished;

  @Builder
  public PlaceRestaurantResponse(PlaceRestaurant placeRestaurant, boolean isWished) {
    this.id = placeRestaurant.getPlace().getId();
    this.name = placeRestaurant.getPlace().getName();
    this.summary = placeRestaurant.getPlace().getSummary();
    this.image = placeRestaurant.getPlace().getFile().getName();
    this.address = addressToString(placeRestaurant.getPlace().getAddress());
    this.averageRating = placeRestaurant.getPlace().getAverageRating();
    this.isWished = isWished;
  }

  public String addressToString(Address address) {
    String addressToString = address.getBuildingName() + " " + address.getRoadName();
    AddressCategory addressCategory = address.getAddressCategory();
    String addressCategoryToString =
        addressCategory.getEmdName() + " " + addressCategory.getSiggName() + " " + addressCategory.getSidoName();
    return addressToString + " " + addressCategoryToString;
  }
}
