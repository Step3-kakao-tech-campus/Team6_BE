package com.example.tripKo.domain.member;


import com.example.tripKo.domain.member.dao.MemberReservationInfoRepository;
import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.member.entity.MemberReservationInfo;
import com.example.tripKo.domain.place.PlaceTestHelper;
import com.example.tripKo.domain.place.entity.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberReservationInfoTestHelper {

  @Autowired
  MemberReservationInfoRepository memberReservationInfoRepository;
  @Autowired
  MemberTestHelper memberTestHelper;
  @Autowired
  PlaceTestHelper placeTestHelper;

  public MemberReservationInfo generate() {
    return this.builder().build();
  }

  public MemberReservationInfoBuilder builder() {
    return new MemberReservationInfoBuilder();
  }

  public final class MemberReservationInfoBuilder {

    private Member member;
    private Long headCount;
    private MemberReservationStatus status;
    private Place place;
    private String reservationDate;
    private String reservationTime;
    private String message;

    public MemberReservationInfoBuilder member(Member member) {
      this.member = member;
      return this;
    }

    public MemberReservationInfoBuilder headCount(Long headCount) {
      this.headCount = headCount;
      return this;
    }

    public MemberReservationInfoBuilder status(MemberReservationStatus status) {
      this.status = status;
      return this;
    }

    public MemberReservationInfoBuilder place(Place place) {
      this.place = place;
      return this;
    }

    public MemberReservationInfoBuilder reservationTime(String reservationTime) {
      this.reservationTime = reservationTime;
      return this;
    }

    public MemberReservationInfoBuilder reservationDate(String reservationDate) {
      this.reservationDate = reservationDate;
      return this;
    }

    public MemberReservationInfoBuilder message(String message) {
      this.message = message;
      return this;
    }

    public MemberReservationInfo build() {
      return memberReservationInfoRepository.save(MemberReservationInfo.builder()
          .member(member != null ? member : memberTestHelper.generate())
          .headCount(headCount != null ? headCount : 10)
          .status(status != null ? status : MemberReservationStatus.예약대기)
          .place(place != null ? place : placeTestHelper.generate())
          .reservationDate(reservationDate != null ? reservationDate : "2023/10/11")
          .reservationTime(reservationTime != null ? reservationTime : "10:00")
          .message(message != null ? message : "고수 뻬주세요")
          .build()
      );
    }
  }


}
