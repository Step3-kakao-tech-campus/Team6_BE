package com.example.tripKo.domain.place.dao;

import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.place.PlaceType;
import com.example.tripKo.domain.place.entity.Place;
import com.example.tripKo.domain.place.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Review findReviewByMemberIdAndPlaceId(Long memberId, Long placeId);



    @Query(value = "select r from Review r where r.place.id = :placeId",
            countQuery = "SELECT COUNT(*) FROM PlaceRestaurant")
    List<Review> findAllByPlaceId(@Param("placeId") Long placeId, Pageable pageable);
}
