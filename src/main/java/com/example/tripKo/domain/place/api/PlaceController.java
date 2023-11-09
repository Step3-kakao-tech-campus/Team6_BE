package com.example.tripKo.domain.place.api;

import com.example.tripKo._core.security.data.JwtUserDetails;
import com.example.tripKo._core.utils.ApiUtils;
import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.place.application.PlaceService;
import com.example.tripKo.domain.place.dto.response.search.PlaceFestivalResponse;
import com.example.tripKo.domain.place.dto.response.search.PlaceResponse;
import com.example.tripKo.domain.place.dto.response.search.PlaceRestaurantResponse;
import com.example.tripKo.domain.place.dto.response.search.PlaceTouristSpotResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "https://kd284b475c6e5a.user-app.krampoline.com:3000")
public class PlaceController {

  private final PlaceService placeService;

  @GetMapping(value = "/search")
  public ResponseEntity<?> findAllByLocation(@RequestParam(value = "location") String location, @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
    Long id = null;
    if (jwtUserDetails != null)
      id = jwtUserDetails.getMember().getId();
    PlaceResponse responseDTO = placeService.findAllByLocation(location, id);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
    return ResponseEntity.ok(apiResult);
  }

  @GetMapping("/search/restaurant")
  public ResponseEntity<?> findRestaurantByLocation(@RequestParam(value = "location") String location,
                                                    @RequestParam(value = "page", defaultValue = "0") Integer page, @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
    Long id = null;
    if (jwtUserDetails != null)
      id = jwtUserDetails.getMember().getId();
    List<PlaceRestaurantResponse> responseDTO = placeService.findRestaurantByLocation(location, page, id);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
    return ResponseEntity.ok(apiResult);
  }

  @GetMapping("/search/festival")
  public ResponseEntity<?> findFestivalByLocation(@RequestParam(value = "location") String location,
                                                  @RequestParam(value = "page", defaultValue = "0") Integer page, @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
    Long id = null;
    if (jwtUserDetails != null)
      id = jwtUserDetails.getMember().getId();
    List<PlaceFestivalResponse> responseDTO = placeService.findFestivalByLocation(location, page, id);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
    return ResponseEntity.ok(apiResult);
  }

  @GetMapping("/search/touristSpot")
  public ResponseEntity<?> findTouristSpotByLocation(@RequestParam(value = "location") String location,
                                                     @RequestParam(value = "page", defaultValue = "0") Integer page, @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
    Long id = null;
    if (jwtUserDetails != null)
      id = jwtUserDetails.getMember().getId();
    List<PlaceTouristSpotResponse> responseDTO = placeService.findTouristSpotByLocation(location, page, id);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
    return ResponseEntity.ok(apiResult);
  }
}
