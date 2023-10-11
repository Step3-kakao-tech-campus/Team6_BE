package com.example.tripKo.domain.food.dao;

import com.example.tripKo.domain.food.entity.FoodContents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodContentsRepository extends JpaRepository<FoodContents, Long> {
}
