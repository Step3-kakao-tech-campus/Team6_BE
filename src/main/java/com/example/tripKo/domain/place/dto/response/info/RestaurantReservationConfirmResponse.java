package com.example.tripKo.domain.place.dto.response.info;

import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.member.entity.MemberReservationInfo;
import lombok.Builder;
import lombok.Getter;

public class RestaurantReservationConfirmResponse {
    private Long id;
    private String reservationDate;
    private String reservationTime;
    private Long placeId;
    private Long headCount;
    private String status;
    private MemberDTO member;


    @Getter
    @Builder
    public static class MemberDTO {
        private String realName;
        private String emailAddress;
    }


    public RestaurantReservationConfirmResponse (MemberReservationInfo memberReservationInfo, Member member) {
        this.id = memberReservationInfo.getId();
        this.reservationDate = memberReservationInfo.getReservationDate();
        this.reservationTime = memberReservationInfo.getReservationTime();
        this.placeId = memberReservationInfo.getPlace().getId();
        this.headCount = memberReservationInfo.getHeadCount();
        this.status = memberReservationInfo.getStatus().name();
        this.member = mapMember(member);
    }

    private MemberDTO mapMember (Member member){
        return MemberDTO.builder()
                .realName(member.getRealName())
                .emailAddress(member.getEmailAddress())
                .build();
    }
}
