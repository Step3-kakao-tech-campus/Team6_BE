package com.example.tripKo.domain.food.dto.response;

import com.example.tripKo.domain.food.FoodCategory;
import com.example.tripKo.domain.food.entity.Food;
import com.example.tripKo.domain.food.entity.FoodContents;
import com.example.tripKo.domain.food.entity.FoodHasPlaceRestaurants;
import com.example.tripKo.domain.place.entity.Address;
import com.example.tripKo.domain.place.entity.AddressCategory;
import com.example.tripKo.domain.place.entity.PlaceRestaurant;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class FoodDetailsResponse {
    private Long foodId;
    private String name;
    private FoodCategory category;
    private List<FoodContentsDTO> contents;

    private List<RestaurantDTO> restaurant;
    private List<String> ingredients;

    @Getter
    @Builder
    public static class FoodContentsDTO {
        private Long page;
        private String description;
        private List<String> images;
    }

    @Getter
    @Builder
    public static class RestaurantDTO {
        private String name;
        private String location;
        private float averageRating;
    }

    public FoodDetailsResponse(Food food) {
        this.foodId = food.getId();
        this.name = food.getName();
        this.category = food.getFoodCategory();
        this.contents = food.getFoodContents().stream()
                .map(this::mapContent)
                .collect(Collectors.toList());
        this.restaurant = food.getFoodHasPlaceRestaurants().stream()
                .map(this::mapRestaurant)
                .collect(Collectors.toList());
        this.ingredients = food.getIngredients();
    }

    private FoodContentsDTO mapContent(FoodContents foodContents) {
        return FoodContentsDTO.builder()
                .page(foodContents.getPage())
                .description(foodContents.getDescription())
                .images(foodContents.getFoodContentsHasFiles().stream()
                        .map(fc -> fc.getFile().getName())
                        .collect(Collectors.toList()))
                .build();
    }

    private RestaurantDTO mapRestaurant(FoodHasPlaceRestaurants foodHasPlaceRestaurants) {
        return RestaurantDTO.builder()
                .name(foodHasPlaceRestaurants.getPlaceRestaurant().getPlace().getName())
                .location(addressToString(foodHasPlaceRestaurants.getPlaceRestaurant().getPlace().getAddress()))
                .averageRating(foodHasPlaceRestaurants.getPlaceRestaurant().getPlace().getAverageRating())
                .build();
    }

    private String addressToString(Address address) {
        String addressToString = address.getBuildingName() + " " + address.getRoadName();
        AddressCategory addressCategory = address.getAddressCategory();
        String addressCategoryToString = addressCategory.getEmdName() + " " + addressCategory.getSiggName() + " " + addressCategory.getSidoName();
        return addressToString + " " + addressCategoryToString;
    }
}
