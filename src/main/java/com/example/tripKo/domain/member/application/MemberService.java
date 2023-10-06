package com.example.tripKo.domain.member.application;

import com.example.tripKo.domain.member.dao.MemberReservationInfoRepository;
import com.example.tripKo.domain.member.dto.response.RestaurantReservationResponse;
import com.example.tripKo.domain.member.entity.MemberReservationInfo;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

  private final MemberReservationInfoRepository memberReservationInfoRepository;

  @Transactional
  public List<RestaurantReservationResponse> getRestaurantReservationInfo() {
    List<MemberReservationInfo> memberReservationInfoList = memberReservationInfoRepository.findAll();
    List<RestaurantReservationResponse> responseList = memberReservationInfoList.stream()
        .map(memberReservationInfo -> RestaurantReservationResponse.from(memberReservationInfo))
        .collect(Collectors.toList());
    return responseList;
  }
}
