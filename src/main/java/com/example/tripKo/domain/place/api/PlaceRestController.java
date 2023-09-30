package com.example.tripKo.domain.place.api;

import com.example.tripKo._core.utils.ApiUtils;
import com.example.tripKo.domain.place.dto.*;
import com.example.tripKo.domain.place.application.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PlaceRestController {
    private final PlaceService placeService;

    @RequestMapping(value = "/{location:^.*(?!h2-console)}", method = RequestMethod.GET)
    public ResponseEntity<?> findAllByLocation(@PathVariable String location) {
        PlaceResponseDTO responseDTO = placeService.findAllByLocation(location);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/{location}/restaurant")
    public ResponseEntity<?> findRestaurantByLocation(@PathVariable String location, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<PlaceRestaurantResponseDTO> responseDTO = placeService.findRestaurantByLocation(location, page);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/{location}/restaurant/{id}")
    public ResponseEntity<?> getRestaurantDetails(@PathVariable String location, @PathVariable long id) {
        PlaceRestaurantDetailsResponseDTO responseDTO = placeService.findRestaurantDetailsById(id);
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