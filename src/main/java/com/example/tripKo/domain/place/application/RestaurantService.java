package com.example.tripKo.domain.place.application;

import com.example.tripKo._core.errors.exception.Exception404;
import com.example.tripKo.domain.member.dao.MemberRepository;
import com.example.tripKo.domain.member.dao.MemberReservationInfoRepository;
import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.member.entity.MemberReservationInfo;
import com.example.tripKo.domain.place.dto.request.RestaurantReservationRequest;
import com.example.tripKo.domain.place.dao.PlaceRestaurantRepository;
import com.example.tripKo.domain.place.dto.response.info.RestaurantReservationConfirmResponse;
import com.example.tripKo.domain.place.dto.response.info.RestaurantReservationSelectResponse;
import com.example.tripKo.domain.place.entity.PlaceRestaurant;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {

    private final PlaceRestaurantRepository placeRestaurantRepository;
    private final MemberReservationInfoRepository memberReservationInfoRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public RestaurantReservationSelectResponse selectRestaurantReservationDate(RestaurantReservationRequest.SelectDTO requestDTO) {
        PlaceRestaurant placeRestaurant = placeRestaurantRepository.findById(requestDTO.getId())
                .orElseThrow(() -> new Exception404("해당하는 식당을 찾을 수 없습니다. id : " + requestDTO.getId()));
        RestaurantReservationSelectResponse ResponseDTO = new RestaurantReservationSelectResponse(placeRestaurant);
        return ResponseDTO;
    }
//    @Transactional
//    public RestaurantReservationConfirmResponse confirmRestaurantReservation(RestaurantReservationRequest.ResultDTO requestDTO) {
//        MemberReservationInfo memberReservationInfo = memberReservationInfoRepository.findById(requestDTO.getMemberId())
//        PlaceRestaurant placeRestaurant = restaurantReservationRepository.findById(id)
//                .orElseThrow(() -> new Exception404("해당하는 식당을 찾을 수 없습니다. id : " + id));
//        Member member = memberRepository
//        RestaurantReservationConfirmResponse ResponseDTO = new RestaurantReservationConfirmResponse(memberReservationInfo, member);
//        return ResponseDTO;
//    }
}