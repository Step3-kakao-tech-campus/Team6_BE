package com.example.tripKo.domain.member.entity;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.example.tripKo.domain.member.MemberReservationStatus;
import com.example.tripKo.domain.place.dto.request.RestaurantReservationConfirmRequest;
import com.example.tripKo.domain.place.entity.Place;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@DynamicInsert
@DynamicUpdate
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "member_reservation_info")
public class MemberReservationInfo {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(nullable = false, updatable = false)
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @Column(nullable = false)
  private Long headCount;

  @Enumerated(value = EnumType.STRING)
  @Column(nullable = false)
  private MemberReservationStatus status;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "place_id", nullable = false)
  private Place place;

  @Column(nullable = false)
  private String reservationDate;

  @Column(nullable = false)
  private String reservationTime;

  @Column
  private String message;


  @Builder
  public MemberReservationInfo(Member member, Long headCount, MemberReservationStatus status, Place place,
      String reservationDate, String reservationTime, String message) {
    this.member = member;
    this.headCount = headCount;
    this.status = status;
    this.place = place;
    this.reservationDate = reservationDate;
    this.reservationTime = reservationTime;
    this.message = message;
  }

  public void setStatus(MemberReservationStatus status) {
    this.status = status;
  }
}
