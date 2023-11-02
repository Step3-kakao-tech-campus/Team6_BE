package com.example.tripKo.domain.member.application;

import com.example.tripKo._core.errors.exception.Exception404;
import com.example.tripKo._core.security.JwtProvider;
import com.example.tripKo._core.security.data.JwtToken;
import com.example.tripKo.domain.member.MemberReservationStatus;
import com.example.tripKo.domain.member.MemberRoleType;
import com.example.tripKo.domain.member.application.convenience.CheckDuplicateService;
import com.example.tripKo.domain.member.dao.MemberRepository;
import com.example.tripKo.domain.member.dao.MemberReservationInfoRepository;
import com.example.tripKo.domain.member.dto.request.SignInRequest;
import com.example.tripKo.domain.member.dto.response.RestaurantReservationResponse;
import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.member.entity.MemberReservationInfo;
import java.util.List;
import java.util.stream.Collectors;

import com.example.tripKo.domain.place.dao.PlaceRepository;
import com.example.tripKo.domain.place.dao.PlaceRestaurantRepository;
import com.example.tripKo.domain.place.dto.request.RestaurantReservationConfirmRequest;
import com.example.tripKo.domain.place.dto.response.info.RestaurantReservationConfirmResponse;
import com.example.tripKo.domain.place.dto.response.info.RestaurantReservationSelectResponse;
import com.example.tripKo.domain.place.entity.Place;
import com.example.tripKo.domain.place.entity.PlaceRestaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
  private final JwtProvider jwtProvider;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final CheckDuplicateService checkDuplicateService;


  @Transactional
  public List<RestaurantReservationResponse> getRestaurantReservationInfo() {
    List<MemberReservationInfo> memberReservationInfoList = memberReservationInfoRepository.findAll();
    List<RestaurantReservationResponse> responseList = memberReservationInfoList.stream()
        .map(memberReservationInfo -> RestaurantReservationResponse.from(memberReservationInfo))
        .collect(Collectors.toList());
    return responseList;
  }

  @Transactional
  public RestaurantReservationSelectResponse selectRestaurantReservationDate(Long id) {
    PlaceRestaurant placeRestaurant = placeRestaurantRepository.findById(id)
            .orElseThrow(() -> new Exception404("해당하는 식당을 찾을 수 없습니다. id : " + id));
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

  @Transactional
  public void signUp(String memberId, String password, String nickName, String realName, String email,
      String nationality) {
//    checkIsDuplicateEmail(email);
//    checkIsDuplicateLoginId(memberId);

    Member member = Member.builder()
        .memberId(memberId)
        .password(passwordEncoder.encode(password))
        .nickName(nickName)
        .realName(realName)
        .emailAddress(email)
        .nationality(nationality)
        .role(MemberRoleType.MEMBER)
        .birthday("700101")
        .build();

    memberRepository.save(member);
  }

  public JwtToken signIn(SignInRequest request) {
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        request.getMemberId(), request.getRawPassword());
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    return jwtProvider.generateToken(authentication);
  }

//  private void checkIsDuplicateEmail(String email) {
//    if (checkDuplicateService.isDuplicateEmail(email)) {
//      throw new Exception404("동일한 이메일이 존재합니다.");
//    }
//  }
//
//  private void checkIsDuplicateLoginId(String memberId) {
//    if (checkDuplicateService.isDuplicateLoginId(memberId)) {
//      throw new Exception404("동일한 아이디가 존재합니다.");
//    }
//  }
}
