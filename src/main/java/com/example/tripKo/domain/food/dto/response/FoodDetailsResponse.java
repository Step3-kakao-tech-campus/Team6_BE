package com.example.tripKo.domain.food.dto.response;

import com.example.tripKo.domain.food.FoodCategory;
import com.example.tripKo.domain.food.entity.Food;
import com.example.tripKo.domain.food.entity.FoodHasPlaceRestaurants;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class FoodDetailsResponse {

  private Long id;
  private String name;
  private FoodCategory category;
  private String description;
  private String mainImage;
  private List<String> foodImage;
  private List<RestaurantDTO> restaurant;
  private List<String> ingredients;

  @Getter
  @Builder
  public static class RestaurantDTO {

    private String name;
    private String location;
    private String image;
    private double averageRating;
  }

  public FoodDetailsResponse(Food food) {
    this.id = food.getId();
    this.name = food.getName();
    this.category = food.getFoodCategory();
    this.description = food.getDescription();
    this.mainImage = food.getFile().getUrl();
    this.foodImage = food.getFoodImages().stream()
        .map(fi -> fi.getFile().getUrl())
        .collect(Collectors.toList());
    this.restaurant = food.getFoodHasPlaceRestaurants().stream()
        .map(this::mapRestaurant)
        .collect(Collectors.toList());
    this.ingredients = food.getIngredients();
  }

  private RestaurantDTO mapRestaurant(FoodHasPlaceRestaurants foodHasPlaceRestaurants) {
    return RestaurantDTO.builder()
        .name(foodHasPlaceRestaurants.getPlaceRestaurant().getPlace().getName())
        .location(foodHasPlaceRestaurants.getPlaceRestaurant().getPlace().getAddress().toString())
        .averageRating(foodHasPlaceRestaurants.getPlaceRestaurant().getPlace().getAverageRating())
        .image(foodHasPlaceRestaurants.getPlaceRestaurant().getPlace().getFile().getUrl())
        .build();
  }
}
