package com.example.tripKo.domain.place.dto.response.info;

import com.example.tripKo.domain.place.PlaceType;
import com.example.tripKo.domain.place.entity.Contents;
import com.example.tripKo.domain.place.entity.ContentsMenu;
import com.example.tripKo.domain.place.entity.PlaceRestaurant;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class RestaurantResponse {

  private long id;
  private String name;
  private String type;
  private String mainImage;
  private String summary;
  private List<Content> contents;
  private double averageRating;
  private String address;
  private Integer holiday;
  private String open;
  private String breakTime;
  private String contactInfo;
  private Boolean isWished = false; //좋아요 기능 개발되면 수정
  private Boolean isReservable = true; //예약 기능 개발되면 수정
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

  public static RestaurantResponse from(PlaceRestaurant placeRestaurant, boolean isWished) {
    return RestaurantResponse.builder()
        .id(placeRestaurant.getPlace().getId())
        .type(PlaceType.RESTAURANT.name())
        .name(placeRestaurant.getPlace().getName())
        .mainImage(placeRestaurant.getPlace().getFile().getUrl())
        .summary(placeRestaurant.getPlace().getSummary())
        .contents(placeRestaurant.getPlace().getContents().stream()
            .map(RestaurantResponse::mapContent)
            .collect(Collectors.toList()))
        .averageRating(placeRestaurant.getPlace().getAverageRating())
        .address(placeRestaurant.getPlace().addressToString(placeRestaurant.getPlace().getAddress()))
        .holiday(placeRestaurant.getHoliday())
        .open(placeRestaurant.getOpeningTime() + "~" + placeRestaurant.getClosingTime())
        .breakTime(placeRestaurant.getBreakStartTime() + "~" + placeRestaurant.getBreakEndTime())
        .contactInfo(placeRestaurant.getContact_info())
        .isWished(isWished)
        .isReservable(true)
        .menus(placeRestaurant.getPlace().getContents().stream()
            .flatMap(contents -> contents.getContentsMenus().stream()
                .map(MenuDTO::new))
            .collect(Collectors.toList()))
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
