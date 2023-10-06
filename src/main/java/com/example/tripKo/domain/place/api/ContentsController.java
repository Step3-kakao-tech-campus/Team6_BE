package com.example.tripKo.domain.place.api;

import com.example.tripKo._core.utils.ApiUtils;
import com.example.tripKo.domain.place.application.ContentsService;
import com.example.tripKo.domain.place.dto.response.info.FestivalResponse;
import com.example.tripKo.domain.place.dto.response.info.TouristSpotResponse;
import com.example.tripKo.domain.place.dto.response.info.RestaurantResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
public class ContentsController {

  private final ContentsService contentsService;

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<?> getRestaurantDetails(@PathVariable Long id) {
        RestaurantResponse responseDTO = contentsService.findRestaurantDetailsById(id);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }

  @GetMapping("/festival/{id}")
  public ResponseEntity<?> getFestivalInfo(@PathVariable Long id) {
    FestivalResponse response = contentsService.getFestivalInfo(id);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
    return ResponseEntity.ok(apiResult);
  }

    @GetMapping("/touristSpot/{id}")
    public ResponseEntity<?> getTouristSpotInfo(@PathVariable Long id) {
        TouristSpotResponse response = contentsService.getTouristSpotInfo(id);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
        return ResponseEntity.ok(apiResult);
    }
}
