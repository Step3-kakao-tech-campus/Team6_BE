package com.example.tripKo.domain.member.dto.response.review;

import com.example.tripKo.domain.place.entity.Address;
import com.example.tripKo.domain.place.entity.AddressCategory;
import com.example.tripKo.domain.place.entity.Review;
import lombok.Builder;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReviewsListResponse {

  private List<ReviewsResponse> restaurant = new ArrayList<>();
  private List<ReviewsResponse> festival = new ArrayList<>();
  private List<ReviewsResponse> touristSpot = new ArrayList<>();

  @Builder
  public ReviewsListResponse(List<Review> restaurant, List<Review> festival, List<Review> touristSpot) {
    this.restaurant = restaurant.stream().map(r -> ReviewsResponse.builder().review(r).build())
        .collect(Collectors.toList());
    this.festival = festival.stream().map(f -> ReviewsResponse.builder().review(f).build())
        .collect(Collectors.toList());
    this.touristSpot = touristSpot.stream().map(t -> ReviewsResponse.builder().review(t).build())
        .collect(Collectors.toList());
  }
}
