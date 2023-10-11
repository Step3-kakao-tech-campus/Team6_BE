package com.example.tripKo.domain.member.api;

import com.example.tripKo._core.utils.ApiUtils;
import com.example.tripKo.domain.member.application.MemberService;
import com.example.tripKo.domain.member.dto.response.RestaurantReservationResponse;
import java.util.List;

import com.example.tripKo.domain.place.dto.request.RestaurantReservationConfirmRequest;
import com.example.tripKo.domain.place.dto.request.RestaurantReservationSelectRequest;
import com.example.tripKo.domain.place.dto.response.info.RestaurantReservationConfirmResponse;
import com.example.tripKo.domain.place.dto.response.info.RestaurantReservationSelectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

  @PostMapping("/restaurant/bookings/calendar")
  public ResponseEntity<?> selectReservationDate(@RequestBody @Valid RestaurantReservationSelectRequest requestDTOs) {
    RestaurantReservationSelectResponse responseDTO = memberService.selectRestaurantReservationDate(requestDTOs);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
    return ResponseEntity.ok(apiResult);
  }

  @PostMapping("/restaurant/bookings")
  public ResponseEntity<?> confirmReservation(@RequestBody @Valid RestaurantReservationConfirmRequest requestDTOs) {
    RestaurantReservationConfirmResponse responseDTO = memberService.confirmRestaurantReservation(requestDTOs);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
    return ResponseEntity.ok(apiResult);
  }

}
