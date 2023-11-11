package com.example.tripKo.domain.place.dto.response.search;

import com.example.tripKo.domain.place.entity.Address;
import com.example.tripKo.domain.place.entity.AddressCategory;
import com.example.tripKo.domain.place.entity.PlaceFestival;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PlaceFestivalResponse {

  private Long id;
  private String name;
  private String summary;
  private String image;
  private String address;
  private double averageRating;
  private Boolean isWished;

  @Builder
  public PlaceFestivalResponse(PlaceFestival placeFestival, boolean isWished) {
    this.id = placeFestival.getPlace().getId();
    this.name = placeFestival.getPlace().getName();
    this.summary = placeFestival.getPlace().getSummary();
    this.image = placeFestival.getPlace().getFile().getUrl();
    this.address = addressToString(placeFestival.getPlace().getAddress());
    this.averageRating = placeFestival.getPlace().getAverageRating();
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
