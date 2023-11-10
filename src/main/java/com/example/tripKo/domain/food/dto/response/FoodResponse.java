package com.example.tripKo.domain.food.dto.response;

import com.example.tripKo.domain.file.entity.File;
import com.example.tripKo.domain.food.FoodCategory;
import com.example.tripKo.domain.food.entity.Food;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
public class FoodResponse {

  private Long id;
  private String name;
  private FoodCategory category;
  private String image;
  private String summary;

  @Builder
  public FoodResponse(Food food) {
    this.id = food.getId();
    this.name = food.getName();
    this.category = food.getFoodCategory();
    this.image = food.getFile().getUrl();
    this.summary = food.getSummary();
  }

}
