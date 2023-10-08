package com.example.tripKo.domain.member.dao;

import com.example.tripKo.domain.member.entity.MemberReservationInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberReservationInfoRepository extends JpaRepository<MemberReservationInfo, Long> {

}
