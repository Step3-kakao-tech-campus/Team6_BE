package com.example.tripKo.domain.food.entity;

import com.example.tripKo.domain.file.entity.File;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@DynamicInsert
@DynamicUpdate
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "food_has_file")
public class FoodHasFile {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private File file;

    @Builder
    public FoodHasFile(Food food, File file) {
        this.food = food;
        this.file = file;
    }
}
