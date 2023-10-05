package com.example.tripKo.domain.contents.dto.response;

import static lombok.AccessLevel.PRIVATE;

import com.example.tripKo.domain.contents.entity.Contents;
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
  private Boolean liked;
  private Boolean isReservable;
  private String period;

  @Builder
  @Getter
  public static class Content {

    private Long page;
    private String description;
    private List<String> image;
  }

  public FestivalResponse(PlaceFestival placeFestival) {
    this.id = placeFestival.getId();
    this.name = placeFestival.getPlace().getName();
    this.averageScore = placeFestival.getPlace().getAverageRating();
    this.mainImage = placeFestival.getPlace().getFile().getName();
    this.contents = placeFestival.getPlace().getContents().stream()
        .map(this::mapContent)
        .collect(Collectors.toList());
    this.address = placeFestival.getPlace().addressToString(placeFestival.getPlace().getAddress());
    this.liked = false;
    this.isReservable = placeFestival.getReservationAvailable();
    this.period = placeFestival.getStartDate() + " ~ " + placeFestival.getEndDate();
  }

  private Content mapContent(Contents contents) {
    return Content.builder()
        .page(contents.getPage())
        .description(contents.getDescription())
        .image(contents.getContentsHasFiles().stream()
            .map(c -> c.getFile().getName())
            .collect(Collectors.toList()))
        .build();
  }

}
