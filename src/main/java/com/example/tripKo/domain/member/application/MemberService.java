package com.example.tripKo.domain.member.application;

import com.example.tripKo._core.errors.exception.Exception404;
import com.example.tripKo.domain.member.MemberReservationStatus;
import com.example.tripKo.domain.member.dao.MemberRepository;
import com.example.tripKo.domain.member.dao.MemberReservationInfoRepository;
import com.example.tripKo.domain.member.dto.response.RestaurantReservationResponse;
import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.member.entity.MemberReservationInfo;
import java.util.List;
import java.util.stream.Collectors;

import com.example.tripKo.domain.place.dao.PlaceRepository;
import com.example.tripKo.domain.place.dao.PlaceRestaurantRepository;
import com.example.tripKo.domain.place.dto.request.RestaurantReservationConfirmRequest;
import com.example.tripKo.domain.place.dto.request.RestaurantReservationSelectRequest;
import com.example.tripKo.domain.place.dto.response.info.RestaurantReservationConfirmResponse;
import com.example.tripKo.domain.place.dto.response.info.RestaurantReservationSelectResponse;
import com.example.tripKo.domain.place.entity.Place;
import com.example.tripKo.domain.place.entity.PlaceRestaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

  private final MemberReservationInfoRepository memberReservationInfoRepository;
  private final MemberRepository memberRepository;
  private final PlaceRestaurantRepository placeRestaurantRepository;
  private final PlaceRepository placeRepository;

  @Transactional
  public List<RestaurantReservationResponse> getRestaurantReservationInfo() {
    List<MemberReservationInfo> memberReservationInfoList = memberReservationInfoRepository.findAll();
    List<RestaurantReservationResponse> responseList = memberReservationInfoList.stream()
        .map(memberReservationInfo -> RestaurantReservationResponse.from(memberReservationInfo))
        .collect(Collectors.toList());
    return responseList;
  }

  @Transactional
  public RestaurantReservationSelectResponse selectRestaurantReservationDate(RestaurantReservationSelectRequest requestDTO) {
    PlaceRestaurant placeRestaurant = placeRestaurantRepository.findById(requestDTO.getId())
            .orElseThrow(() -> new Exception404("해당하는 식당을 찾을 수 없습니다. id : " + requestDTO.getId()));
    RestaurantReservationSelectResponse ResponseDTO = new RestaurantReservationSelectResponse(placeRestaurant);
    return ResponseDTO;
  }

  @Transactional
  public RestaurantReservationConfirmResponse confirmRestaurantReservation(RestaurantReservationConfirmRequest requestDTO) {
    Member memberInfo = memberRepository.findById(requestDTO.getReservation().getMemberId())
            .orElseThrow(() -> new Exception404("유저를 찾을 수 없습니다. id : " + requestDTO.getReservation().getMemberId()));
    Place place = placeRepository.findById(requestDTO.getReservation().getPlaceId())
            .orElseThrow(() -> new Exception404("해당하는 식당을 찾을 수 없습니다. id : " + requestDTO.getReservation().getPlaceId()));
    MemberReservationInfo saveMemberReservationInfo = new MemberReservationInfo(
            memberInfo,
            MemberReservationStatus.예약완료,
            place,
            requestDTO);
    memberReservationInfoRepository.save(saveMemberReservationInfo);

    MemberReservationInfo memberReservationInfo = memberReservationInfoRepository.findById(requestDTO.getReservation().getId())
            .orElseThrow(() -> new Exception404("예약이 완료되지 않았습니다. id : " + requestDTO.getReservation().getId()));
    RestaurantReservationConfirmResponse ResponseDTO = new RestaurantReservationConfirmResponse(memberReservationInfo);
    return ResponseDTO;
  }
}
