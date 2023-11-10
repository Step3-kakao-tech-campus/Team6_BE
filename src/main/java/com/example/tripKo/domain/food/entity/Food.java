package com.example.tripKo.domain.food.entity;

import com.example.tripKo.domain.file.entity.File;
import com.example.tripKo.domain.food.FoodCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "food")
public class Food {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 100, nullable = false)
  private String name;

  @Column(length = 1000, nullable = false)
  private String summary;

  @Column(length = 1000, nullable = false)
  private String keyword;

  @Column
  private int view;

  @Enumerated(EnumType.STRING)
  private FoodCategory foodCategory;

  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "file_id", nullable = false)
  private File file; //메인 이미지

    /*
    @OneToMany(fetch = LAZY, mappedBy = "food")
    private final List<FoodContents> foodContents = new ArrayList<>();
     */

  @Column
  private String description;

  @OneToMany(fetch = LAZY, mappedBy = "food")
  private final List<FoodHasFile> foodImages = new ArrayList<>();

  @OneToMany(fetch = LAZY, mappedBy = "food")
  private final List<FoodHasPlaceRestaurants> foodHasPlaceRestaurants = new ArrayList<>();

  @ElementCollection
  @CollectionTable(name = "food_ingredients", joinColumns = @JoinColumn(name = "food_id"))
  @Column(name = "ingredients_name")
  private final List<String> ingredients = new ArrayList<>();


  @Builder
  public Food(String name, String summary, String keyword, FoodCategory foodCategory, File file, String description) {
    this.name = name;
    this.summary = summary;
    this.keyword = keyword;
    this.foodCategory = foodCategory;
    this.file = file;
    this.description = description;
    this.view = 0;
  }

  public void addFoodHasFile(FoodHasFile foodHasFile) {
    this.foodImages.add(foodHasFile);
  }


  public void addIngredients(String ingredients) {
    this.ingredients.add(ingredients);
  }

  public void updateView() {
    this.view++;
  }
}
