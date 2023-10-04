package com.example.tripKo.domain.contents.dto.response;

import static lombok.AccessLevel.PRIVATE;

import com.example.tripKo.domain.address.entity.Address;
import com.example.tripKo.domain.address.entity.AddressCategory;
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
  private List<String> images;
  private String address;
  private String description;
  private String imagesPath;
  private Boolean isWished;
  private Boolean isReservable;
  private String period;

  public FestivalResponse(PlaceFestival placeFestival) {
    this.id = placeFestival.getId();
    this.name = placeFestival.getPlace().getName();
    this.averageScore = placeFestival.getPlace().getAverageRating();
    this.mainImage = placeFestival.getPlace().getFile().getName();
    this.images = placeFestival.getPlace().getContents().stream()
        .flatMap(c -> c.getContentsHasFiles().stream())
        .map(c -> c.getFile().getName())
        .collect(Collectors.toList());
    this.address = addressToString(placeFestival.getPlace().getAddress());
    this.description = placeFestival.getPlace().getSummary();
    this.isWished = false;
    this.isReservable = placeFestival.getReservationAvailable();
    this.period = placeFestival.getStartDate() + " ~ " + placeFestival.getEndDate();
  }

  public String addressToString(Address address) {
    String addressToString = address.getBuildingName() + " " + address.getRoadName();
    AddressCategory addressCategory = address.getAddressCategory();
    String addressCategoryToString = addressCategory.getEmdName() + " " + addressCategory.getSiggName() + " " + addressCategory.getSidoName();
    return addressToString + " " + addressCategoryToString;
  }

}
