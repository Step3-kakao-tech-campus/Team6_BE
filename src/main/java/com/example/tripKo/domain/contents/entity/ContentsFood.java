package com.example.tripKo.domain.contents.entity;

import com.example.tripKo.domain.BaseTimeEntity;
import com.example.tripKo.domain.file.dao.ContentsFoodFileRepository;
import com.example.tripKo.domain.file.entity.ContentsFile;
import com.example.tripKo.domain.file.entity.ContentsFoodFile;
import com.example.tripKo.domain.food.entity.Food;
import com.example.tripKo.domain.place.entity.Place;
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
@Table(name = "contents_food")
public class ContentsFood extends BaseTimeEntity {

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

  @OneToMany(mappedBy = "contentsFood")
  private final List<ContentsFoodFile> contentsFoodFiles = new ArrayList<>();

  @Builder
  public ContentsFood(Long page, String description, Food food) {
    this.page = page;
    this.description = description;
    this.food = food;
  }

  public void addContentsFoodFile(ContentsFoodFile contentsFoodFile) {
    contentsFoodFiles.add(contentsFoodFile);
  }
}
