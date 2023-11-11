package com.example.tripKo.domain.member.application;

import static com.example.tripKo._core.errors.ErrorCode.EMAIL_ALREADY_EXIST;
import static com.example.tripKo._core.errors.ErrorCode.FESTIVAL_ID_CANNOT_FOUND;
import static com.example.tripKo._core.errors.ErrorCode.MEMBERID_ALREADY_EXIST;
import static com.example.tripKo._core.errors.ErrorCode.RESERVATION_NOT_COMPLETE;
import static com.example.tripKo._core.errors.ErrorCode.RESTAURANT_ID_CANNOT_FOUND;
import static com.example.tripKo._core.errors.ErrorCode.REVIEW_CANNOT_FOUND;
import static com.example.tripKo._core.errors.ErrorCode.REVIEW_NOT_MINE;

import com.example.tripKo._core.errors.ErrorCode;
import com.example.tripKo._core.errors.exception.BusinessException;
import com.example.tripKo._core.S3.ImageS3Service;
import com.example.tripKo._core.errors.exception.Exception400;
import com.example.tripKo._core.errors.exception.Exception404;
import com.example.tripKo._core.errors.exception.Exception500;
import com.example.tripKo._core.security.JwtProvider;
import com.example.tripKo._core.security.data.JwtToken;
import com.example.tripKo.domain.file.dao.FileRepository;
import com.example.tripKo.domain.member.MemberReservationStatus;
import com.example.tripKo.domain.member.MemberRoleType;
import com.example.tripKo.domain.member.application.convenience.CheckDuplicateService;
import com.example.tripKo.domain.member.dao.MemberRepository;
import com.example.tripKo.domain.member.dao.MemberReservationInfoRepository;
import com.example.tripKo.domain.member.dto.request.SignInRequest;
import com.example.tripKo.domain.member.dto.response.FestivalReservationResponse;
import com.example.tripKo.domain.member.dto.request.userInfo.UserInfoRequest;
import com.example.tripKo.domain.member.dto.response.RestaurantReservationResponse;
import com.example.tripKo.domain.member.dto.response.review.ReviewsListResponse;
import com.example.tripKo.domain.member.dto.response.review.ReviewsResponse;
import com.example.tripKo.domain.member.dto.response.userInfo.UserInfoResponse;
import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.member.entity.MemberReservationInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.tripKo.domain.place.dao.PlaceFestivalRepository;
import com.example.tripKo.domain.place.PlaceType;
import com.example.tripKo.domain.place.dao.PlaceRepository;
import com.example.tripKo.domain.place.dao.PlaceRestaurantRepository;
import com.example.tripKo.domain.place.dto.request.FestivalReservationConfirmRequest;
import com.example.tripKo.domain.place.dao.ReviewRepository;
import com.example.tripKo.domain.place.dto.request.RestaurantReservationConfirmRequest;
import com.example.tripKo.domain.place.dto.response.info.FestivalReservationConfirmResponse;
import com.example.tripKo.domain.place.dto.response.info.FestivalReservationSelectResponse;
import com.example.tripKo.domain.place.dto.request.ReviewUpdateRequest;
import com.example.tripKo.domain.place.dto.response.info.RestaurantReservationConfirmResponse;
import com.example.tripKo.domain.place.dto.response.info.RestaurantReservationSelectResponse;
import com.example.tripKo.domain.place.entity.Place;
import com.example.tripKo.domain.place.entity.PlaceFestival;
import com.example.tripKo.domain.place.entity.PlaceRestaurant;
import com.example.tripKo.domain.place.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

  private final MemberReservationInfoRepository memberReservationInfoRepository;
  private final MemberRepository memberRepository;
  private final PlaceRestaurantRepository placeRestaurantRepository;
  private final PlaceFestivalRepository placeFestivalRepository;
  private final PlaceRepository placeRepository;
  private final JwtProvider jwtProvider;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final CheckDuplicateService checkDuplicateService;
  private final FileRepository fileRepository;
  private final ReviewRepository reviewRepository;
  private final ImageS3Service imageS3Service;

  @Transactional
  public UserInfoResponse getUserInfo(Member member) {
    UserInfoResponse userInfoResponse = UserInfoResponse.builder().member(member).build();
    return userInfoResponse;
  }

  @Transactional
  public void setUserInfo(Member member, UserInfoRequest userInfoRequest) {
    //이메일 중복 여부 체크
    Member emailCheck = memberRepository.findByEmailAddressAndMemberIdNot(userInfoRequest.getEmail(),
        member.getMemberId()).orElse(null);
    if (emailCheck != null) {
      throw new BusinessException(userInfoRequest.getEmail(), "Email", EMAIL_ALREADY_EXIST);
    }
    member.updateUserInfo(userInfoRequest);
    memberRepository.save(member);
  }

  @Transactional
  public void setUserInfoImage(Member member, MultipartFile image) {
    com.example.tripKo.domain.file.entity.File file = imageS3Service.uploadImage(image);
    file = fileRepository.save(file);

    member.updateFile(file);
    memberRepository.save(member);
  }

  @Transactional
  public List<ReviewsResponse> getRestaurantReviews(Member member, int page) {
    Pageable pageable = PageRequest.of(page, 10);
    List<Review> reviews = reviewRepository.findAllByMemberAndPlaceType(member, PlaceType.RESTAURANT, pageable);
    List<ReviewsResponse> reviewsResponses = reviews.stream().map(r -> ReviewsResponse.builder().review(r).build())
        .collect(Collectors.toList());
    return reviewsResponses;
  }

  @Transactional
  public List<ReviewsResponse> getFestivalReviews(Member member, int page) {
    Pageable pageable = PageRequest.of(page, 10);
    List<Review> reviews = reviewRepository.findAllByMemberAndPlaceType(member, PlaceType.FESTIVAL, pageable);
    List<ReviewsResponse> reviewsResponses = reviews.stream().map(r -> ReviewsResponse.builder().review(r).build())
        .collect(Collectors.toList());
    return reviewsResponses;
  }

  @Transactional
  public List<ReviewsResponse> getTouristSpotReviews(Member member, int page) {
    Pageable pageable = PageRequest.of(page, 10);
    List<Review> reviews = reviewRepository.findAllByMemberAndPlaceType(member, PlaceType.TOURIST_SPOT, pageable);
    List<ReviewsResponse> reviewsResponses = reviews.stream().map(r -> ReviewsResponse.builder().review(r).build())
        .collect(Collectors.toList());
    return reviewsResponses;
  }

  @Transactional
  public ReviewsResponse getReviewDetail(Member member, Long id) {
    Review review = reviewRepository.findById(id)
        .orElseThrow(() -> new BusinessException(id, "id", REVIEW_CANNOT_FOUND));
    if (!review.getMember().equals(member)) {
      new BusinessException(id, "id", REVIEW_NOT_MINE);
    }

    ReviewsResponse reviewsResponse = ReviewsResponse.builder().review(review).build();
    return reviewsResponse;
  }

  @Transactional
  public ReviewsListResponse getAllReviews(Member member, int page) {
    Pageable pageable = PageRequest.of(page, 10);
    List<Review> reviews = reviewRepository.findAllByMember(member, pageable);
    List<Review> restaurant = new ArrayList<>();
    List<Review> festival = new ArrayList<>();
    List<Review> touristSpot = new ArrayList<>();
    for (Review r : reviews) {
      switch (r.getType()) {
        case RESTAURANT:
          restaurant.add(r);
          break;
        case FESTIVAL:
          festival.add(r);
          break;
        case TOURIST_SPOT:
          touristSpot.add(r);
          break;
      }
    }
    ReviewsListResponse reviewsListResponse = ReviewsListResponse.builder().restaurant(restaurant).festival(festival)
        .touristSpot(touristSpot).build();
    return reviewsListResponse;
  }

  @Transactional
  public List<RestaurantReservationResponse> getRestaurantReservationInfo(Member member) {
    List<MemberReservationInfo> memberReservationInfoList = memberReservationInfoRepository.findAllByMemberAndPlaceType(member, PlaceType.RESTAURANT);
    List<RestaurantReservationResponse> responseList = memberReservationInfoList.stream()
        .map(memberReservationInfo -> RestaurantReservationResponse.from(memberReservationInfo))
        .collect(Collectors.toList());
    return responseList;
  }

  @Transactional
  public RestaurantReservationSelectResponse selectRestaurantReservationDate(Long id) {
    PlaceRestaurant placeRestaurant = placeRestaurantRepository.findByPlaceId(id)
        .orElseThrow(() -> new BusinessException(id, "id", RESTAURANT_ID_CANNOT_FOUND));
    RestaurantReservationSelectResponse ResponseDTO = new RestaurantReservationSelectResponse(placeRestaurant);
    return ResponseDTO;
  }

  @Transactional
  public RestaurantReservationConfirmResponse confirmRestaurantReservation(
      Member member,
      RestaurantReservationConfirmRequest requestDTO) {
//    Member memberInfo = memberRepository.findById(requestDTO.getReservation().getMemberId())
//        .orElseThrow(() -> new Exception404("유저를 찾을 수 없습니다. id : " + requestDTO.getReservation().getMemberId()));
    Place place = placeRepository.findById(requestDTO.getReservation().getPlaceId())
        .orElseThrow(() -> new BusinessException(requestDTO.getReservation().getPlaceId(), "id",
            RESTAURANT_ID_CANNOT_FOUND));
    MemberReservationInfo saveMemberReservationInfo = new MemberReservationInfo(
        member,
        requestDTO.getReservation().getHeadCount(),
        MemberReservationStatus.예약완료,
        place,
        requestDTO.getReservation().getReservationDate(),
        requestDTO.getReservation().getReservationTime(),
        requestDTO.getReservation().getMessage()
    );
    MemberReservationInfo memberReservationInfo = memberReservationInfoRepository.save(saveMemberReservationInfo);
    RestaurantReservationConfirmResponse ResponseDTO = new RestaurantReservationConfirmResponse(memberReservationInfo);
    return ResponseDTO;
  }

  @Transactional
  public List<FestivalReservationResponse> getFestivalReservationInfo(Member member) {
    List<MemberReservationInfo> memberReservationInfoList = memberReservationInfoRepository.findAllByMemberAndPlaceType(member, PlaceType.FESTIVAL);
    List<FestivalReservationResponse> responseList = memberReservationInfoList.stream()
        .map(memberReservationInfo -> FestivalReservationResponse.from(memberReservationInfo))
        .collect(Collectors.toList());
    return responseList;
  }

  @Transactional
  public FestivalReservationSelectResponse selectFestivalReservationDate(Long id) {
    PlaceFestival placeFestival = placeFestivalRepository.findByPlaceId(id)
        .orElseThrow(() -> new BusinessException(id, "id", FESTIVAL_ID_CANNOT_FOUND));
    FestivalReservationSelectResponse ResponseDTO = new FestivalReservationSelectResponse(placeFestival);
    return ResponseDTO;
  }

  @Transactional
  public FestivalReservationConfirmResponse confirmFestivalReservation(
      Member member,
      FestivalReservationConfirmRequest requestDTO) {
//    Member memberInfo = memberRepository.findById(requestDTO.getReservation().getMemberId())
//        .orElseThrow(() -> new Exception404("유저를 찾을 수 없습니다. id : " + requestDTO.getReservation().getMemberId()));
    Place place = placeRepository.findById(requestDTO.getReservation().getPlaceId())
        .orElseThrow(
            () -> new BusinessException(requestDTO.getReservation().getPlaceId(), "id", FESTIVAL_ID_CANNOT_FOUND));
    MemberReservationInfo saveMemberReservationInfo = new MemberReservationInfo(
        member,
        requestDTO.getReservation().getHeadCount(),
        MemberReservationStatus.예약완료,
        place,
        requestDTO.getReservation().getReservationDate(),
        "", // 축제 예약은 시간 선택 기능이 없으니 빈 string으로 넘겨줌
        requestDTO.getReservation().getMessage()
    );
    MemberReservationInfo memberReservationInfo = memberReservationInfoRepository.save(saveMemberReservationInfo);

    FestivalReservationConfirmResponse ResponseDTO = new FestivalReservationConfirmResponse(memberReservationInfo);
    return ResponseDTO;
  }


  @Transactional
  public void signUp(String memberId, String password, String nickName, String realName, String email,
      String nationality) {
    checkIsDuplicateEmail(email);
    checkIsDuplicateLoginId(memberId);

    Member member = Member.builder()
        .memberId(memberId)
        .password(passwordEncoder.encode(password))
        .nickName(nickName)
        .realName(realName)
        .emailAddress(email)
        .nationality(nationality)
        .role(MemberRoleType.MEMBER)
        .build();

    memberRepository.save(member);
  }

  public JwtToken signIn(SignInRequest request) {
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        request.getMemberId(), request.getPassword());
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    return jwtProvider.generateToken(authentication);
  }

  private void checkIsDuplicateEmail(String email) {
    if (checkDuplicateService.isDuplicateEmail(email)) {
      throw new BusinessException(email, "email", EMAIL_ALREADY_EXIST);
    }
  }

  private void checkIsDuplicateLoginId(String memberId) {
    if (checkDuplicateService.isDuplicateLoginId(memberId)) {
      throw new BusinessException(memberId, "memberId", MEMBERID_ALREADY_EXIST);
    }
  }
}
