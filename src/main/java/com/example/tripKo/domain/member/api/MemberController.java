package com.example.tripKo.domain.member.api;

import com.example.tripKo._core.security.data.JwtToken;
import com.example.tripKo._core.utils.ApiUtils;
import com.example.tripKo.domain.member.application.MemberService;
import com.example.tripKo.domain.member.dao.MemberRepository;
import com.example.tripKo.domain.member.dto.request.MemberRequest;
import com.example.tripKo.domain.member.dto.response.RestaurantReservationResponse;
import java.util.List;
import com.example.tripKo.domain.place.dto.request.RestaurantReservationConfirmRequest;
import com.example.tripKo.domain.place.dto.response.info.RestaurantReservationConfirmResponse;
import com.example.tripKo.domain.place.dto.response.info.RestaurantReservationSelectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
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
  public ResponseEntity<?> join(@RequestBody MemberRequest.JoinDTO requestDTO, Errors errors) {
    memberService.join(requestDTO);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
    return ResponseEntity.ok().body(apiResult);
  }

  @PostMapping("/sign-in")
  public ResponseEntity<Void> login(@RequestBody MemberRequest.LoginDTO loginDTO, Errors errors) {
    JwtToken jwtToken = memberService.login(loginDTO);
    return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, jwtToken.getGrantType() + jwtToken.getAccessToken())
            .header("Refresh-Token", jwtToken.getRefreshToken())
            .build();
  }

}
