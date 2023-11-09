package com.example.tripKo.domain.place.dto.response.info;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class HomeResponse {

  private List<SelectRestaurantResponse> restaurants;
  private List<SelectFestivalResponse> festivals;
  private List<SelectTouristSpotResponse> touristSpots;

  @Getter
  @Builder
  @AllArgsConstructor
  public static class SelectRestaurantResponse {

    private Long id;
    private String name;
    private String summary;
    private String image;
    private String address;
    private double averageRating;
    private Boolean isWished;
  }

  @Getter
  @Builder
  @AllArgsConstructor
  public static class SelectFestivalResponse {

    private Long id;
    private String name;
    private String summary;
    private String image;
    private String address;
    private Boolean isWished;
  }

  @Getter
  @Builder
  @AllArgsConstructor
  public static class SelectTouristSpotResponse {

    private Long id;
    private String name;
    private String summary;
    private String image;
    private String address;
    private Boolean isWished;
  }

  public static HomeResponse from(List<RestaurantResponse> restaurantResponses,
      List<FestivalResponse> festivalResponses,
      List<TouristSpotResponse> touristSpotResponses) {
    List<SelectRestaurantResponse> selectRestaurantResponses = restaurantResponses.stream()
        .map(restaurantResponse -> SelectRestaurantResponse.builder()
            .id(restaurantResponse.getId())
            .name(restaurantResponse.getName())
            .summary(restaurantResponse.getContents().get(0).getDescription()) // 데이터 없으면 에러나요 주의!
            .image(restaurantResponse.getMainImage())
            .address(restaurantResponse.getAddress())
            .averageRating(restaurantResponse.getAverageScore())
            .isWished(restaurantResponse.getIsWished())
            .build())
        .collect(Collectors.toList());

    List<SelectFestivalResponse> selectFestivalResponses = festivalResponses.stream()
        .map(festivalResponse -> SelectFestivalResponse.builder()
            .id(festivalResponse.getId())
            .name(festivalResponse.getName())
            .image(festivalResponse.getMainImage())
            .summary(festivalResponse.getContents().get(0).getDescription()) // 데이터 없으면 에러나요 주의!
            .address(festivalResponse.getAddress())
            .isWished(festivalResponse.getIsWished())
            .build())
        .collect(Collectors.toList());

    List<SelectTouristSpotResponse> selectTouristSpotResponses = touristSpotResponses.stream()
        .map(touristSpotResponse -> SelectTouristSpotResponse.builder()
            .id(touristSpotResponse.getId())
            .name(touristSpotResponse.getName())
            .image(touristSpotResponse.getMainImage())
            .summary(touristSpotResponse.getContents().get(0).getDescription()) // 데이터 없으면 에러나요 주의!
            .address(touristSpotResponse.getAddress())
            .isWished(touristSpotResponse.getIsWished())
            .build())
        .collect(Collectors.toList());

    return HomeResponse.builder()
        .restaurants(selectRestaurantResponses)
        .festivals(selectFestivalResponses)
        .touristSpots(selectTouristSpotResponses)
        .build();
  }
}
