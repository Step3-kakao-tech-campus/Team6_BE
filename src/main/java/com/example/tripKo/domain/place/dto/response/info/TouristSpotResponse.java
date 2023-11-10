package com.example.tripKo.domain.place.dto.response.info;

import static lombok.AccessLevel.PRIVATE;

import com.example.tripKo.domain.place.PlaceType;
import com.example.tripKo.domain.place.entity.Contents;
import com.example.tripKo.domain.place.entity.PlaceTouristSpot;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
public class TouristSpotResponse {

  private Long id;
  private String type;
  private String name;
  private String summary;
  private double averageRating;
  private String mainImage;
  private List<Content> contents;
  private String address;
  private Boolean isWished = false;

  @Builder
  @Getter
  public static class Content {

    private Long page;
    private String description;
    private List<String> image;
  }

  public static TouristSpotResponse from(PlaceTouristSpot placeTouristSpot, boolean isWished) {
    return TouristSpotResponse.builder()
        .id(placeTouristSpot.getId())
        .type(PlaceType.TOURIST_SPOT.name())
        .name(placeTouristSpot.getPlace().getName())
        .summary(placeTouristSpot.getPlace().getSummary())
        .averageRating(placeTouristSpot.getPlace().getAverageRating())
        .mainImage(placeTouristSpot.getPlace().getFile().getUrl())
        .contents(placeTouristSpot.getPlace().getContents().stream()
            .map(TouristSpotResponse::mapContent)
            .collect(Collectors.toList()))
        .address(placeTouristSpot.getPlace().addressToString(placeTouristSpot.getPlace().getAddress()))
        .isWished(isWished)
        .build();
  }

  private static Content mapContent(Contents contents) {
    return Content.builder()
        .page(contents.getPage())
        .description(contents.getDescription())
        .image(contents.getContentsHasFiles().stream()
            .map(c -> c.getFile().getUrl())
            .collect(Collectors.toList()))
        .build();
  }

}
