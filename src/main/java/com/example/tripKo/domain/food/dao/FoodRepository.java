package com.example.tripKo.domain.food.dao;

import com.example.tripKo.domain.food.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
}
