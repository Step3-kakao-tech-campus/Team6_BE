package com.example.tripKo.domain.member.dto.response.review;

import com.example.tripKo.domain.place.entity.Address;
import com.example.tripKo.domain.place.entity.AddressCategory;
import com.example.tripKo.domain.place.entity.Place;
import com.example.tripKo.domain.place.entity.Review;
import lombok.Builder;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReviewsResponse {

  private Long reviewId;
  private Long placeId;
  private String placeName;
  private String address;
  private String visitDay;
  private int rating;
  private String description;
  private List<String> image = new ArrayList<>();

  @Builder
  public ReviewsResponse(Review review) {
    this.reviewId = review.getId();
    this.placeId = review.getPlace().getId();
    this.placeName = review.getPlace().getName();
    this.address = addressToString(review.getPlace().getAddress());
    this.visitDay = review.getUsageDate();
    this.rating = review.getScore();
    this.description = review.getDescription();
    this.image = review.getReviewHasFiles().stream()
        .map(r -> createFileNameWithPaths(r.getFile().getName()))
        .collect(Collectors.toList());
  }

  private String createFileNameWithPaths(String filename) {
    return "src" + File.separator
        + "main" + File.separator
        + "resources" + File.separator
        + "reviews" + File.separator
        + "images" + File.separator
        + filename;
  }

  private String addressToString(Address address) {
    String addressToString = address.getBuildingName() + " " + address.getRoadName();
    AddressCategory addressCategory = address.getAddressCategory();
    String addressCategoryToString =
        addressCategory.getEmdName() + " " + addressCategory.getSiggName() + " " + addressCategory.getSidoName();
    return addressToString + " " + addressCategoryToString;
  }


}
