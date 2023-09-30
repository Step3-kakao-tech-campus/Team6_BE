package com.example.tripKo.domain.contents.dto.response;

import static lombok.AccessLevel.PRIVATE;

import com.example.tripKo.domain.contents.entity.Contents;
import com.example.tripKo.domain.file.entity.ContentsFile;
import java.util.List;
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
  private List<ContentsFile> images;
  private String address;
  private String description;
  private String imagesPath;
  private Boolean isWished;
  private Boolean isReservable;
  private String startDate;
  private String endDate;

  public static FestivalResponse from(Contents contents) {
    return FestivalResponse.builder()
        .id(contents.getId())
        .name(contents.getPlace().getName())
        .averageScore(contents.getPlace().getAverageRating())
        .mainImage(contents.getPlace().getFile().getName())
        .images(contents.getContentsFiles())
        .address(contents.addressToString(contents.getPlace().getAddress()))
        .description(contents.getDescription())
        .isWished(false)
        .isReservable(contents.getPlace().getPlaceFestival().getReservationAvailable())
        .startDate(contents.getPlace().getPlaceFestival().getStartDate())
        .endDate(contents.getPlace().getPlaceFestival().getEndDate())
        .build();
  }

}
