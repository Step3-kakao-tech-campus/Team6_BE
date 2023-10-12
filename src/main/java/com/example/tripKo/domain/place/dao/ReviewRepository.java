package com.example.tripKo.domain.place.dao;

import com.example.tripKo.domain.place.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
