package com.example.tripKo.domain.member.dto.response;

import static lombok.AccessLevel.PRIVATE;

import com.example.tripKo.domain.member.entity.MemberReservationInfo;
import com.example.tripKo.domain.place.PlaceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
public class RestaurantReservationResponse {

  private Long id;
  private Long placeId;
  private PlaceType type;
  private String name;
  private String address;
  private String image;
  private String date;
  private String time;
  private String message;
  private String status;
  private Long headCount;

  public static RestaurantReservationResponse from(MemberReservationInfo memberReservationInfo) {
    return RestaurantReservationResponse.builder()
        .id(memberReservationInfo.getId())
        .placeId(memberReservationInfo.getPlace().getId())
        .type(PlaceType.RESTAURANT)
        .name(memberReservationInfo.getPlace().getName())
        .address(memberReservationInfo.getPlace().addressToString(memberReservationInfo.getPlace().getAddress()))
        .image(memberReservationInfo.getPlace().getFile().getUrl())
        .date(memberReservationInfo.getReservationDate())
        .time(memberReservationInfo.getReservationTime())
        .message(memberReservationInfo.getMessage())
        .status(memberReservationInfo.getStatus().name())
        .headCount(memberReservationInfo.getHeadCount())
        .build();
  }
}
