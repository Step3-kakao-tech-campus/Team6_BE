package com.example.tripKo.domain.place.api;

import com.example.tripKo._core.utils.ApiUtils;
import com.example.tripKo.domain.place.application.RestaurantService;
import com.example.tripKo.domain.place.dto.request.RestaurantReservationConfirmRequest;
import com.example.tripKo.domain.place.dto.request.RestaurantReservationSelectRequest;
import com.example.tripKo.domain.place.dto.response.info.RestaurantReservationConfirmResponse;
import com.example.tripKo.domain.place.dto.response.info.RestaurantReservationSelectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RestaurantRestController {

    private final RestaurantService restaurantService;

    @PostMapping("/restaurant/bookings/calendar")
    public ResponseEntity<?> selectReservationDate(@RequestBody @Validated RestaurantReservationSelectRequest requestDTOs) {
        RestaurantReservationSelectResponse responseDTO = restaurantService.selectRestaurantReservationDate(requestDTOs);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }

    @PostMapping("/restaurant/bookings")
    public ResponseEntity<?> confirmReservation(@RequestBody @Validated RestaurantReservationConfirmRequest requestDTOs) {
        RestaurantReservationConfirmResponse responseDTO = restaurantService.confirmRestaurantReservation(requestDTOs);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }
}
