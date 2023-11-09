package com.example.tripKo.domain.place.api;

import com.example.tripKo._core.security.data.JwtUserDetails;
import com.example.tripKo.domain.place.application.ContentsService;
import com.example.tripKo.domain.place.dto.response.info.FestivalResponse;
import com.example.tripKo.domain.place.dto.response.info.HomeResponse;
import com.example.tripKo.domain.place.dto.response.info.HomeResponse.SelectFestivalResponse;
import com.example.tripKo.domain.place.dto.response.info.HomeResponse.SelectRestaurantResponse;
import com.example.tripKo.domain.place.dto.response.info.HomeResponse.SelectTouristSpotResponse;
import com.example.tripKo.domain.place.dto.response.info.TouristSpotResponse;
import com.example.tripKo.domain.place.dto.response.info.RestaurantResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@Validated
@RestController
@RequiredArgsConstructor
public class ContentsController {

  private final ContentsService contentsService;

  @GetMapping("/restaurant/{id}")
  public ResponseEntity<RestaurantResponse> getRestaurantDetails(@PathVariable Long id, @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
    Long memberId = null;
    if (jwtUserDetails != null)
      memberId = jwtUserDetails.getMember().getId();
    RestaurantResponse response = contentsService.findRestaurantDetailsById(id, memberId);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/festival/{id}")
  public ResponseEntity<FestivalResponse> getFestivalInfo(@PathVariable Long id, @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
    Long memberId = null;
    if (jwtUserDetails != null)
      memberId = jwtUserDetails.getMember().getId();
    FestivalResponse response = contentsService.getFestivalInfo(id, memberId);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/touristSpot/{id}")
  public ResponseEntity<TouristSpotResponse> getTouristSpotInfo(@PathVariable Long id, @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
    Long memberId = null;
    if (jwtUserDetails != null)
      memberId = jwtUserDetails.getMember().getId();
    TouristSpotResponse response = contentsService.getTouristSpotInfo(id, memberId);
    return ResponseEntity.ok(response);
  }
    
  @CrossOrigin(origins = "https://kd284b475c6e5a.user-app.krampoline.com:3000")
  @GetMapping("/home")
  public ResponseEntity<HomeResponse> getHomeInfo(@AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
    Long memberId = null;
    if (jwtUserDetails != null)
      memberId = jwtUserDetails.getMember().getId();
    List<SelectRestaurantResponse> restaurants = contentsService.getHomeInfo(memberId).getRestaurants();
    List<SelectFestivalResponse> festivals = contentsService.getHomeInfo(memberId).getFestivals();
    List<SelectTouristSpotResponse> touristSpots = contentsService.getHomeInfo(memberId).getTouristSpots();

    HomeResponse homeResponse = new HomeResponse(restaurants, festivals, touristSpots);
    return ResponseEntity.ok(homeResponse);
  }
}
