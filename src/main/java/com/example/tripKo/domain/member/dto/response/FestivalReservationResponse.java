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
public class FestivalReservationResponse {

  private Long id;
  private Long placeId;
  private PlaceType type;
  private String name;
  private String address;
  private String image;
  private String date;
  private String message;
  private String status;
  private Long headCount;

  public static FestivalReservationResponse from(MemberReservationInfo memberReservationInfo) {
    return FestivalReservationResponse.builder()
        .id(memberReservationInfo.getId())
        .placeId(memberReservationInfo.getPlace().getId())
        .type(PlaceType.FESTIVAL)
        .name(memberReservationInfo.getPlace().getName())
        .address(memberReservationInfo.getPlace().addressToString(memberReservationInfo.getPlace().getAddress()))
        .image(memberReservationInfo.getPlace().getFile().getName())
        .date(memberReservationInfo.getReservationDate())
        .message(memberReservationInfo.getMessage())
        .status(memberReservationInfo.getStatus().name())
        .headCount(memberReservationInfo.getHeadCount())
        .build();
  }
}
