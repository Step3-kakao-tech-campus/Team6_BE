package com.example.tripKo.domain.place.dao;

import com.example.tripKo.domain.place.entity.ReviewHasFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ReviewFileRepository extends JpaRepository<ReviewHasFile, Long>{

    @Query(value = "select r from ReviewHasFile r where r.review.id = :reviewId",
            countQuery = "SELECT COUNT(*) FROM Review")
    List<ReviewHasFile> findAllByReviewId(@Param("reviewId") Long reviewId);

    @Modifying
    @Transactional
    @Query(value = "delete from ReviewHasFile r where r.review.id = :reviewId")
    void deleteAllByReviewId(@Param("reviewId") Long reviewId);
}
