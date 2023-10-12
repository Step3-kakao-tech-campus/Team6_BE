package com.example.tripKo.domain.place.dao;

import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.place.entity.Place;
import com.example.tripKo.domain.place.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select r from Review r where r.member.id = :memberId" +
            " and r.place.id = :placeId" +
            " and r.usageDate = :usageDate")
    Review findReviewByMemberIdAndPlaceIdAndUsageDate(@Param("memberId") Long memberId,
                                                  @Param("placeId") Long placeId,
                                                  @Param("usageDate") String usageDate);

    @Query(value = "select r from Review r where r.place.id = :placeId",
            countQuery = "SELECT COUNT(*) FROM PlaceRestaurant")
    List<Review> findAllByPlaceId(@Param("placeId") Long placeId, Pageable pageable);
}
