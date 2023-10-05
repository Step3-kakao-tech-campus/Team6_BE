package com.example.tripKo.domain.member.dto.response;

import static lombok.AccessLevel.PRIVATE;

import com.example.tripKo.domain.member.entity.MemberReservationInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
public class RestaurantReservationResponse {

  private Long id;
  private Long placeId;
  private String name;
  private String address;
  private String image;
  private String date;
  private String reservationTime;
  private String message;
  private String status;
  private Long headCount;

  public static RestaurantReservationResponse from(MemberReservationInfo memberReservationInfo) {
    return RestaurantReservationResponse.builder()
        .id(memberReservationInfo.getId())
        .placeId(memberReservationInfo.getPlace().getId())
        .name(memberReservationInfo.getPlace().getName())
        .address(memberReservationInfo.getPlace().addressToString(memberReservationInfo.getPlace().getAddress()))
        .image(memberReservationInfo.getPlace().getFile().getName())
        .date(memberReservationInfo.getReservationDate())
        .reservationTime(memberReservationInfo.getReservationTime())
        .message(memberReservationInfo.getMessage())
        .status(memberReservationInfo.getStatus().name())
        .headCount(memberReservationInfo.getHeadCount())
        .build();
  }
}
