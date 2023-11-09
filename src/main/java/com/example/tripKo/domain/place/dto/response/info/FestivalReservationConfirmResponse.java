package com.example.tripKo.domain.place.dto.response.info;

import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.member.entity.MemberReservationInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;


@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
public class FestivalReservationConfirmResponse {
    private Long id;
    private String reservationDate;
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


    public FestivalReservationConfirmResponse (MemberReservationInfo memberReservationInfo) {
        this.id = memberReservationInfo.getId();
        this.reservationDate = memberReservationInfo.getReservationDate();
        this.placeId = memberReservationInfo.getPlace().getId();
        this.headCount = memberReservationInfo.getHeadCount();
        this.status = memberReservationInfo.getStatus().name();
        this.member = mapMember(memberReservationInfo.getMember());
    }

    private MemberDTO mapMember (Member member){
        return MemberDTO.builder()
                .realName(member.getRealName())
                .emailAddress(member.getEmailAddress())
                .build();
    }
}
