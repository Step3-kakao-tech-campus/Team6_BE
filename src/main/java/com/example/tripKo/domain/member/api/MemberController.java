package com.example.tripKo.domain.member.api;

import com.example.tripKo._core.security.data.JwtToken;
import com.example.tripKo._core.security.data.JwtUserDetails;
import com.example.tripKo._core.utils.ApiUtils;
import com.example.tripKo.domain.member.application.MemberService;
import com.example.tripKo.domain.member.dto.request.SignInRequest;
import com.example.tripKo.domain.member.dto.request.SignUpRequest;
import com.example.tripKo.domain.member.dto.request.userInfo.UserInfoRequest;
import com.example.tripKo.domain.member.dto.response.FestivalReservationResponse;
import com.example.tripKo.domain.member.dto.request.userInfo.UserInfoRequest;
import com.example.tripKo.domain.member.dto.response.RestaurantReservationResponse;
import java.util.List;

import com.example.tripKo.domain.member.dto.response.review.ReviewsListResponse;
import com.example.tripKo.domain.member.dto.response.review.ReviewsResponse;
import com.example.tripKo.domain.member.dto.response.userInfo.UserInfoResponse;
import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.place.dto.request.FestivalReservationConfirmRequest;
import com.example.tripKo.domain.place.dto.request.RestaurantReservationConfirmRequest;
import com.example.tripKo.domain.place.dto.response.info.FestivalReservationConfirmResponse;
import com.example.tripKo.domain.place.dto.response.info.FestivalReservationSelectResponse;
import com.example.tripKo.domain.place.dto.response.info.RestaurantReservationConfirmResponse;
import com.example.tripKo.domain.place.dto.response.info.RestaurantReservationSelectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  @GetMapping("/userinfo")
  public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
    Member member = jwtUserDetails.getMember();
    UserInfoResponse response = memberService.getUserInfo(member);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
    return ResponseEntity.ok(apiResult);
  }

  @PatchMapping("/userinfo/edit")
  public ResponseEntity<?> setUserInfo(@AuthenticationPrincipal JwtUserDetails jwtUserDetails, @RequestBody @Valid UserInfoRequest userInfoRequest) {
    Member member = jwtUserDetails.getMember();
    memberService.setUserInfo(member, userInfoRequest);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
    return ResponseEntity.ok(apiResult);
  }

  @PostMapping(value = "/userinfo/image")
  public ResponseEntity<?> setUserInfoImage(@AuthenticationPrincipal JwtUserDetails jwtUserDetails, @Validated @RequestParam("image") MultipartFile image) {
    Member member = jwtUserDetails.getMember();
    memberService.setUserInfoImage(member, image);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
    return ResponseEntity.ok(apiResult);
  }

  @GetMapping("/userinfo/reservations/restaurant")
  public ResponseEntity<?> getRestaurantReservationInfo(@AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
    Member member = jwtUserDetails.getMember();
    List<RestaurantReservationResponse> response = memberService.getRestaurantReservationInfo(member);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
    return ResponseEntity.ok(apiResult);
  }

  @GetMapping("/userinfo/reviews/restaurant")
  public ResponseEntity<?> getRestaurantReviews(@AuthenticationPrincipal JwtUserDetails jwtUserDetails, @RequestParam(value = "page", defaultValue = "0") Integer page) {
    Member member = jwtUserDetails.getMember();
    List<ReviewsResponse> response = memberService.getRestaurantReviews(member, page);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
    return ResponseEntity.ok(apiResult);
  }

  @GetMapping("/userinfo/reviews/festival")
  public ResponseEntity<?> getFestivalReviews(@AuthenticationPrincipal JwtUserDetails jwtUserDetails, @RequestParam(value = "page", defaultValue = "0") Integer page) {
    Member member = jwtUserDetails.getMember();
    List<ReviewsResponse> response = memberService.getFestivalReviews(member, page);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
    return ResponseEntity.ok(apiResult);
  }

  @GetMapping("/userinfo/reviews/touristSpot")
  public ResponseEntity<?> getTouristSpotReviews(@AuthenticationPrincipal JwtUserDetails jwtUserDetails, @RequestParam(value = "page", defaultValue = "0") Integer page) {
    Member member = jwtUserDetails.getMember();
    List<ReviewsResponse> response = memberService.getTouristSpotReviews(member, page);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
    return ResponseEntity.ok(apiResult);
  }

  @GetMapping("/userinfo/reviews/{id}")
  public ResponseEntity<?> getReviewDetail(@AuthenticationPrincipal JwtUserDetails jwtUserDetails, @RequestParam(value = "id") Long id) {
    Member member = jwtUserDetails.getMember();
    ReviewsResponse response = memberService.getReviewDetail(member, id);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
    return ResponseEntity.ok(apiResult);
  }

  @GetMapping("/userinfo/reviews")
  public ResponseEntity<?> getAllReviews(@AuthenticationPrincipal JwtUserDetails jwtUserDetails, @RequestParam(value = "page", defaultValue = "0") Integer page) {
    Member member = jwtUserDetails.getMember();
    ReviewsListResponse response = memberService.getAllReviews(member, page);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
    return ResponseEntity.ok(apiResult);
  }

  @GetMapping("/restaurant/bookings/calendar/{id}")
  public ResponseEntity<?> selectRestaurantReservationDate(@PathVariable Long id) {
    RestaurantReservationSelectResponse responseDTO = memberService.selectRestaurantReservationDate(id);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
    return ResponseEntity.ok(apiResult);
  }

  @PostMapping("/restaurant/bookings")
  public ResponseEntity<?> confirmRestaurantReservation(@AuthenticationPrincipal JwtUserDetails jwtUserDetails, @RequestBody @Valid RestaurantReservationConfirmRequest requestDTOs) {
    Member member = jwtUserDetails.getMember();
    RestaurantReservationConfirmResponse responseDTO = memberService.confirmRestaurantReservation(member, requestDTOs);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
    return ResponseEntity.ok(apiResult);
  }
  @GetMapping("/userinfo/reservations/festival")
  public ResponseEntity<?> getFestivalReservationInfo(@AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
    Member member = jwtUserDetails.getMember();
    List<FestivalReservationResponse> response = memberService.getFestivalReservationInfo(member);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
    return ResponseEntity.ok(apiResult);
  }

  @GetMapping("/festival/bookings/calendar/{id}")
  public ResponseEntity<?> selectFestivalReservationDate(@PathVariable Long id) {
    FestivalReservationSelectResponse responseDTO = memberService.selectFestivalReservationDate(id);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
    return ResponseEntity.ok(apiResult);
  }

  @PostMapping("/festival/bookings")
  public ResponseEntity<?> confirmFestivalReservation(@AuthenticationPrincipal JwtUserDetails jwtUserDetails, @RequestBody @Valid FestivalReservationConfirmRequest requestDTOs) {
    Member member = jwtUserDetails.getMember();
    FestivalReservationConfirmResponse responseDTO = memberService.confirmFestivalReservation(member, requestDTOs);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
    return ResponseEntity.ok(apiResult);
  }

  @PostMapping("/register")
  public ResponseEntity<Void> signUp(@RequestBody @Valid SignUpRequest request) {
    memberService.signUp(request.getMemberId(), request.getPassword(), request.getNickName(), request.getRealName(),
        request.getEmail(), request.getNationality());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("/login")
  public ResponseEntity<Void> signIn(@RequestBody @Valid SignInRequest request) {
    JwtToken jwtToken = memberService.signIn(request);
    return ResponseEntity.ok()
        .header(HttpHeaders.AUTHORIZATION, jwtToken.getGrantType() + jwtToken.getAccessToken())
        .header("Refresh-Token", jwtToken.getRefreshToken())
        .build();
  }

}
