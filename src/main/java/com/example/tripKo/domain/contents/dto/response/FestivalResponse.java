package com.example.tripKo.domain.contents.dto.response;

import static lombok.AccessLevel.PRIVATE;

import com.example.tripKo.domain.contents.entity.Contents;
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
  private List<String> images;
  private String address;
  private String description;
  private String imagesPath;
  private Boolean isWished;
  private Boolean isReservable;
  private String period;

  public static FestivalResponse from(Contents contents) {
    return FestivalResponse.builder()
        .id(contents.getId())
        .name(contents.getPlace().getName())
        .averageScore(contents.getPlace().getAverageRating())
        .mainImage(contents.getPlace().getFile().getName())
        .images(contents.getContentsFiles().stream()
            .map(c->c.getFile().getName())
            .collect(Collectors.toList()))
        .address(contents.addressToString(contents.getPlace().getAddress()))
        .description(contents.getDescription())
        .isWished(false)
        .isReservable(contents.getPlace().getPlaceFestival().getReservationAvailable())
        .period(contents.getPlace().getPlaceFestival().getStartDate() + "~" + contents.getPlace().getPlaceFestival()
            .getEndDate())
        .build();
  }

}
