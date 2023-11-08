package com.example.tripKo.domain.place.api;

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
  public ResponseEntity<RestaurantResponse> getRestaurantDetails(@PathVariable Long id) {
    RestaurantResponse response = contentsService.findRestaurantDetailsById(id);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/festival/{id}")
  public ResponseEntity<FestivalResponse> getFestivalInfo(@PathVariable Long id) {
    FestivalResponse response = contentsService.getFestivalInfo(id);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/touristSpot/{id}")
  public ResponseEntity<TouristSpotResponse> getTouristSpotInfo(@PathVariable Long id) {
    TouristSpotResponse response = contentsService.getTouristSpotInfo(id);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/home")
  public ResponseEntity<HomeResponse> getHomeInfo() {
    List<SelectRestaurantResponse> restaurants = contentsService.getHomeInfo().getRestaurants();
    List<SelectFestivalResponse> festivals = contentsService.getHomeInfo().getFestivals();
    List<SelectTouristSpotResponse> touristSpots = contentsService.getHomeInfo().getTouristSpots();

    HomeResponse homeResponse = new HomeResponse(restaurants, festivals, touristSpots);
    return ResponseEntity.ok(homeResponse);
  }
}
