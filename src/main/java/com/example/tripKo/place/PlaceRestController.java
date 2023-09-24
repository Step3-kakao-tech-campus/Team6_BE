package com.example.tripKo.place;

import com.example.tripKo._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}