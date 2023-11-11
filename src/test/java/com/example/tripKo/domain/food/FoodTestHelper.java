package com.example.tripKo.domain.food;

import com.example.tripKo.domain.file.FileTestHelper;
import com.example.tripKo.domain.file.entity.File;
import com.example.tripKo.domain.food.dao.FoodRepository;
import com.example.tripKo.domain.food.entity.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FoodTestHelper {

  @Autowired
  FoodRepository foodRepository;
  @Autowired
  FileTestHelper fileTestHelper;

  public Food generate() {
    return this.builder().build();
  }

  public FoodBuilder builder() {
    return new FoodBuilder();
  }

  public final class FoodBuilder {

    private String name;
    private String summary;
    private String keyword;
    private FoodCategory foodCategory;
    private File file;
    private String description;
    private int view;

    public FoodBuilder name(String name) {
      this.name = name;
      return this;
    }

    public FoodBuilder summary(String summary) {
      this.summary = summary;
      return this;
    }

    public FoodBuilder foodCategory(FoodCategory foodCategory) {
      this.foodCategory = foodCategory;
      return this;
    }

    public FoodBuilder keyword(String keyword) {
      this.keyword = keyword;
      return this;
    }

    public FoodBuilder file(File file) {
      this.file = file;
      return this;
    }

    public FoodBuilder description(String description) {
      this.description = description;
      return this;
    }

    public Food build() {
      return foodRepository.save(Food.builder()
          .name(name != null ? name : "라면")
          .summary(summary != null ? summary : "한국대표")
          .keyword(keyword != null ? keyword : "키워드")
          .foodCategory(foodCategory != null ? foodCategory : FoodCategory.KOREAN)
          .file(file != null ? file : fileTestHelper.generate())
          .description(description != null ? description : "맛있어요")
          .build());
    }

  }
}
