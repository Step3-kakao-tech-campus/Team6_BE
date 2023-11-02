package com.example.tripKo.domain.member.api;

import com.example.tripKo._core.security.data.JwtToken;
import com.example.tripKo._core.utils.ApiUtils;
import com.example.tripKo.domain.member.application.MemberService;
import com.example.tripKo.domain.member.dto.request.SignInRequest;
import com.example.tripKo.domain.member.dto.request.SignUpRequest;
import com.example.tripKo.domain.member.dto.response.RestaurantReservationResponse;
import java.util.List;
import com.example.tripKo.domain.place.dto.request.RestaurantReservationConfirmRequest;
import com.example.tripKo.domain.place.dto.response.info.RestaurantReservationConfirmResponse;
import com.example.tripKo.domain.place.dto.response.info.RestaurantReservationSelectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  @GetMapping("/userinfo/reservations/restaurant")
  public ResponseEntity<?> getRestaurantReservationInfo() {
    List<RestaurantReservationResponse> response = memberService.getRestaurantReservationInfo();
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
    return ResponseEntity.ok(apiResult);
  }

  @GetMapping("/restaurant/bookings/calendar/{id}")
  public ResponseEntity<?> selectReservationDate(@PathVariable Long id) {
    RestaurantReservationSelectResponse responseDTO = memberService.selectRestaurantReservationDate(id);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
    return ResponseEntity.ok(apiResult);
  }

  @PostMapping("/restaurant/bookings")
  public ResponseEntity<?> confirmReservation(@RequestBody @Valid RestaurantReservationConfirmRequest requestDTOs) {
    RestaurantReservationConfirmResponse responseDTO = memberService.confirmRestaurantReservation(requestDTOs);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
    return ResponseEntity.ok(apiResult);
  }

  @PostMapping("/sign-up")
  public ResponseEntity<Void> signUp(@RequestBody SignUpRequest request) {
    memberService.signUp(request.getMemberId(), request.getPassword(), request.getNickName(), request.getRealName(),
        request.getEmail(), request.getNationality());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("/sign-in")
  public ResponseEntity<Void> signIn(@RequestBody @Valid SignInRequest request) {
    JwtToken jwtToken = memberService.signIn(request);
    return ResponseEntity.ok()
        .header(HttpHeaders.AUTHORIZATION, jwtToken.getGrantType() + jwtToken.getAccessToken())
        .header("Refresh-Token", jwtToken.getRefreshToken())
        .build();
  }

}
