package com.example.tripKo.domain.member.dao;

import com.example.tripKo.domain.member.MemberReservationStatus;
import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.member.entity.MemberReservationInfo;
import com.example.tripKo.domain.place.PlaceType;
import com.example.tripKo.domain.place.entity.Place;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberReservationInfoRepository extends JpaRepository<MemberReservationInfo, Long> {
    @Query("select m from MemberReservationInfo m where m.member = :member and m.place.placeType = :placeType")
    List<MemberReservationInfo> findAllByMemberAndPlaceType(Member member, PlaceType placeType);

    MemberReservationInfo findByMemberAndPlaceAndStatus(Member member, Place place, MemberReservationStatus status);
}
