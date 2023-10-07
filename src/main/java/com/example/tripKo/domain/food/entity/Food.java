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
@Table(name="food")
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
    private File file;

    @OneToMany(fetch = LAZY, mappedBy = "food")
    private final List<FoodContents> foodContents = new ArrayList<>();

    @Builder
    public Food(String name, String summary, String keyword, FoodCategory foodCategory, File file) {
        this.name = name;
        this.summary = summary;
        this.keyword = keyword;
        this.foodCategory = foodCategory;
        this.file = file;
        this.view = 0;
    }

    public void addFoodContents(FoodContents foodContents) {
        this.foodContents.add(foodContents);
    }

    public void updateView() {
        this.view++;
    }
}
