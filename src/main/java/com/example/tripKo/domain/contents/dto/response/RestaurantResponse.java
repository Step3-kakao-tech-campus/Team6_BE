package com.example.tripKo.domain.contents.dto.response;

import com.example.tripKo.domain.address.entity.Address;
import com.example.tripKo.domain.address.entity.AddressCategory;
import com.example.tripKo.domain.contents.entity.Contents;
import com.example.tripKo.domain.contents.entity.ContentsMenu;
import com.example.tripKo.domain.place.entity.PlaceRestaurant;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RestaurantResponse {

  private long id;
  private String name;
  private String mainImage;
  private List<Content> contents;
  private float averageScore;
  private String address;
  private String holidayDate;
  private String open;
  private String breakTime;
  private String contactInfo;
  private Boolean liked = false; //좋아요 기능 개발되면 수정
  private Boolean reservable = false; //예약 기능 개발되면 수정
  private List<MenuDTO> menus;

  @Getter
  public static class MenuDTO {

    private long id;
    private String name;
    private String image;
    private String description;
    private Long price;

    @Builder
    MenuDTO(ContentsMenu menu) {
      id = menu.getId();
      name = menu.getName();
      image = menu.getFile().getName();
      description = menu.getDescription();
      price = menu.getPrice();
    }
  }

  @Builder
  @Getter
  public static class Content {

    private Long page;
    private String description;
    private List<String> image;
  }

  public RestaurantResponse(PlaceRestaurant placeRestaurant) {
    id = placeRestaurant.getId();
    name = placeRestaurant.getPlace().getName();
    mainImage = placeRestaurant.getPlace().getFile().getName();
    contents = placeRestaurant.getPlace().getContents().stream()
        .map(this::mapContent)
        .collect(Collectors.toList());
    averageScore = placeRestaurant.getPlace().getAverageRating();
    address = placeRestaurant.getPlace().addressToString(placeRestaurant.getPlace().getAddress());
    holidayDate = placeRestaurant.getHolidayDate();
    open = placeRestaurant.getOpeningTime() + "~" + placeRestaurant.getClosingTime();
    breakTime = placeRestaurant.getBreakStartTime() + "~" + placeRestaurant.getBreakEndTime();
    contactInfo = placeRestaurant.getContact_info();
    menus = placeRestaurant.getPlace().getContents().stream()
        .flatMap(c -> c.getContentsMenus().stream())
        .map(MenuDTO::new)
        .collect(Collectors.toList());
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
