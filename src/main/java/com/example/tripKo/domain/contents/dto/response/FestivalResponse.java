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

  @Builder
  public FestivalResponse(Contents contents) {
    this.id = contents.getId();
    this.name = contents.getPlace().getName();
    this.averageScore = contents.getPlace().getAverageRating();
    this.mainImage = contents.getPlace().getFile().getName();
    this.address = contents.addressToString(contents.getPlace().getAddress());
    this.description = contents.getDescription();
    this.isWished = false;
    this.isReservable = contents.getPlace().getPlaceFestival().getReservationAvailable();
    this.startDate = contents.getPlace().getPlaceFestival().getStartDate();
    this.endDate = contents.getPlace().getPlaceFestival().getEndDate();
  }
}
