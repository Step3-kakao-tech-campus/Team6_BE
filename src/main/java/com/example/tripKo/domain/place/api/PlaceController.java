package com.example.tripKo.domain.place.api;

import com.example.tripKo._core.utils.ApiUtils;
import com.example.tripKo.domain.place.application.PlaceService;
import com.example.tripKo.domain.place.dto.response.PlaceFestivalResponse;
import com.example.tripKo.domain.place.dto.response.PlaceResponse;
import com.example.tripKo.domain.place.dto.response.PlaceRestaurantResponse;
import com.example.tripKo.domain.place.dto.response.PlaceTouristSpotResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PlaceController {
    private final PlaceService placeService;

    @RequestMapping(value = "/{location:^.*(?!h2-console)}", method = RequestMethod.GET)
    public ResponseEntity<?> findAllByLocation(@PathVariable String location) {
        PlaceResponse responseDTO = placeService.findAllByLocation(location);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/{location}/restaurant")
    public ResponseEntity<?> findRestaurantByLocation(@PathVariable String location, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<PlaceRestaurantResponse> responseDTO = placeService.findRestaurantByLocation(location, page);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/{location}/festival")
    public ResponseEntity<?> findFestivalByLocation(@PathVariable String location, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<PlaceFestivalResponse> responseDTO = placeService.findFestivalByLocation(location, page);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/{location}/touristSpot")
    public ResponseEntity<?> findTouristSpotByLocation(@PathVariable String location, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<PlaceTouristSpotResponse> responseDTO = placeService.findTouristSpotByLocation(location, page);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }
}