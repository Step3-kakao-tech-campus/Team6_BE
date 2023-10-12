package com.example.tripKo.domain.place.entity;

import com.example.tripKo.domain.file.entity.File;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.FetchType.LAZY;

@DynamicInsert
@DynamicUpdate
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "review_has_file")
public class ReviewHasFile {
    @GeneratedValue(strategy = IDENTITY)
    @Id
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private File file;

    @Builder
    public ReviewHasFile(Review review, File file){
        this.review = review;
        this.file = file;

        review.getReviewHasFiles().add(this);
    }
}
