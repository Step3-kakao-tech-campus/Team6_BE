package com.example.tripKo.domain.file.entity;

import com.example.tripKo.domain.BaseTimeEntity;
import com.example.tripKo.domain.contents.entity.ContentsFood;
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
@Table(name = "contents_food_has_file")
public class ContentsFoodFile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "contents_food_id", nullable = false)
    private ContentsFood contentsFood;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private File file;

    @Builder
    public ContentsFoodFile(ContentsFood contentsFood, File file) {
        this.contentsFood = contentsFood;
        this.file = file;
    }

}
