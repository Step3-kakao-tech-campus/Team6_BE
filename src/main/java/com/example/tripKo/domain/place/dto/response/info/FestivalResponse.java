package com.example.tripKo.domain.place.dto.response.info;

import static lombok.AccessLevel.PRIVATE;

import com.example.tripKo.domain.place.entity.Contents;
import com.example.tripKo.domain.place.entity.PlaceFestival;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
public class FestivalResponse {

  private Long id;
  private String name;
  private Float averageScore;
  private String mainImage;
  private List<Content> contents;
  private String address;
  private Boolean isWished;
  private Boolean isReservable;
  private String period;

  @Builder
  @Getter
  public static class Content {

    private Long page;
    private String description;
    private List<String> image;
  }

  public static FestivalResponse from(PlaceFestival placeFestival, boolean isWished) {
    return FestivalResponse.builder()
        .id(placeFestival.getId())
        .name(placeFestival.getPlace().getName())
        .averageScore(placeFestival.getPlace().getAverageRating())
        .mainImage(placeFestival.getPlace().getFile().getName())
        .contents(placeFestival.getPlace().getContents().stream()
            .map(FestivalResponse::mapContent)
            .collect(Collectors.toList()))
        .address(placeFestival.getPlace().addressToString(placeFestival.getPlace().getAddress()))
        .isWished(isWished)
        .isReservable(placeFestival.getReservationAvailable())
        .period(placeFestival.getStartDate() + " ~ " + placeFestival.getEndDate())
        .build();
  }

  private static Content mapContent(Contents contents) {
    return Content.builder()
        .page(contents.getPage())
        .description(contents.getDescription())
        .image(contents.getContentsHasFiles().stream()
            .map(c -> c.getFile().getName())
            .collect(Collectors.toList()))
        .build();
  }

}
