package com.example.tripKo.domain.place;

import com.example.tripKo._core.utils.ApiUtils;
import com.example.tripKo.domain.place.festival.PlaceFestivalResponseDTO;
import com.example.tripKo.domain.place.restaurant.PlaceRestaurantResponseDTO;
import com.example.tripKo.domain.place.touristSpot.PlaceTouristSpotResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PlaceRestController {
    private final PlaceService placeService;

    @GetMapping("/{location}")
    public ResponseEntity<?> findAllByLocation(@PathVariable String location) {
        PlaceResponseDTO responseDTO = placeService.findAllByLocation(location);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/{location}/restaurants")
    public ResponseEntity<?> findRestaurantByLocation(@PathVariable String location, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<PlaceRestaurantResponseDTO> responseDTO = placeService.findRestaurantByLocation(location, page);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/{location}/festival")
    public ResponseEntity<?> findFestivalByLocation(@PathVariable String location, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<PlaceFestivalResponseDTO> responseDTO = placeService.findFestivalByLocation(location, page);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/{location}/touristSpot")
    public ResponseEntity<?> findTouristSpotByLocation(@PathVariable String location, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<PlaceTouristSpotResponseDTO> responseDTO = placeService.findTouristSpotByLocation(location, page);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }
}