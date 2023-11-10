package com.example.tripKo.domain.member.dto.response.wishlist;

import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.place.entity.*;
import lombok.Builder;
import lombok.Getter;

@Getter
public class WishlistResponse {

  private Long id;
  private String name;
  private String summary;
  private String image;
  private String address;
  private double averageRating;
  private Boolean isWished;

  @Builder(builderMethodName = "PlaceFestivalBuilder", buildMethodName = "PlaceFestivalBuild")
  public WishlistResponse(PlaceFestival placeFestival) {
    this.id = placeFestival.getPlace().getId();
    this.name = placeFestival.getPlace().getName();
    this.summary = placeFestival.getPlace().getSummary();
    this.image = placeFestival.getPlace().getFile().getUrl();
    this.address = addressToString(placeFestival.getPlace().getAddress());
    this.averageRating = placeFestival.getPlace().getAverageRating();
    this.isWished = true;
  }

  @Builder(builderMethodName = "PlaceRestaurantBuilder", buildMethodName = "PlaceRestaurantBuild")
  public WishlistResponse(PlaceRestaurant placeRestaurant) {
    this.id = placeRestaurant.getPlace().getId();
    this.name = placeRestaurant.getPlace().getName();
    this.summary = placeRestaurant.getPlace().getSummary();
    this.image = placeRestaurant.getPlace().getFile().getUrl();
    this.address = addressToString(placeRestaurant.getPlace().getAddress());
    this.averageRating = placeRestaurant.getPlace().getAverageRating();
    this.isWished = true;
  }

  @Builder(builderMethodName = "PlaceTouristSpotBuilder", buildMethodName = "PlaceTouristSpotBuild")
  public WishlistResponse(PlaceTouristSpot placeTouristSpot) {
    this.id = placeTouristSpot.getPlace().getId();
    this.name = placeTouristSpot.getPlace().getName();
    this.summary = placeTouristSpot.getPlace().getSummary();
    this.image = placeTouristSpot.getPlace().getFile().getUrl();
    this.address = addressToString(placeTouristSpot.getPlace().getAddress());
    this.averageRating = placeTouristSpot.getPlace().getAverageRating();
    this.isWished = true;
  }

  public String addressToString(Address address) {
    String addressToString = address.getBuildingName() + " " + address.getRoadName();
    AddressCategory addressCategory = address.getAddressCategory();
    String addressCategoryToString =
        addressCategory.getEmdName() + " " + addressCategory.getSiggName() + " " + addressCategory.getSidoName();
    return addressToString + " " + addressCategoryToString;
  }

}
