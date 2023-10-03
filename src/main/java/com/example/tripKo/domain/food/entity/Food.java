package com.example.tripKo.domain.food.entity;

import com.example.tripKo.domain.address.entity.Address;
import com.example.tripKo.domain.contents.entity.Contents;
import com.example.tripKo.domain.contents.entity.ContentsFood;
import com.example.tripKo.domain.file.entity.File;
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

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private File file;

    @OneToMany(fetch = LAZY, mappedBy = "food")
    private final List<ContentsFood> contentsFood = new ArrayList<>();

    @Builder
    public Food(String name, String summary, String keyword, File file) {
        this.name = name;
        this.summary = summary;
        this.keyword = keyword;
        this.file = file;
    }

    public void addContentsFood(ContentsFood contentsFood) {
        this.contentsFood.add(contentsFood);
    }
}
