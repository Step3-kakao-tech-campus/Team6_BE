package com.example.tripKo.domain.member.dao;

import com.example.tripKo.domain.member.MemberReservationStatus;
import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.member.entity.MemberReservationInfo;
import com.example.tripKo.domain.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberReservationInfoRepository extends JpaRepository<MemberReservationInfo, Long> {
    List<MemberReservationInfo> findAllByMember(Member member);

    MemberReservationInfo findByMemberAndPlaceAndStatus(Member member, Place place, MemberReservationStatus status);
}
