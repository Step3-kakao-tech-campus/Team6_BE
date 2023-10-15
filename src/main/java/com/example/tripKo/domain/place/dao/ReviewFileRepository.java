package com.example.tripKo.domain.place.dao;

import com.example.tripKo.domain.place.entity.ReviewHasFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewFileRepository extends JpaRepository<ReviewHasFile, Long>{
}
