package com.example.tripKo.domain.food.api;

import com.example.tripKo._core.utils.ApiUtils;
import com.example.tripKo.domain.food.application.FoodService;
import com.example.tripKo.domain.food.dto.response.FoodDetailsResponse;
import com.example.tripKo.domain.food.dto.response.FoodResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FoodController {

  private final FoodService foodService;

  @GetMapping("/foods")
  public ResponseEntity<?> findByKeyword(@RequestParam(value = "query", defaultValue = "") String query,
      @RequestParam(value = "sort", defaultValue = "recommended") String sort) {
    List<FoodResponse> responseDTO = foodService.findByKeyword(query, sort);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
    return ResponseEntity.ok(apiResult);
  }


  @GetMapping("/foods/{foodId}")
  public ResponseEntity<?> findByFoodId(@PathVariable Long foodId) {
    FoodDetailsResponse responseDTO = foodService.getFoodInfo(foodId);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
    return ResponseEntity.ok(apiResult);
  }
}
