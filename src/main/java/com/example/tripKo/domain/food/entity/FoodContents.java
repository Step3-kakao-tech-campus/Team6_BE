package com.example.tripKo.domain.food.entity;

import com.example.tripKo.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@DynamicInsert
@DynamicUpdate
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "food_contents")
public class FoodContents extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(nullable = false, updatable = false)
  private Long id;

  @Column(nullable = false)
  private Long page;

  @Column(nullable = false)
  private String description;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "food_id", nullable = false)
  private Food food;

  @OneToMany(mappedBy = "foodContents")
  private final List<FoodContentsHasFile> foodContentsHasFiles = new ArrayList<>();

  @Builder
  public FoodContents(Long page, String description, Food food) {
    this.page = page;
    this.description = description;
    this.food = food;
  }

  public void addFoodContentsFile(FoodContentsHasFile foodContentsHasFile) {
    foodContentsHasFiles.add(foodContentsHasFile);
  }
}
