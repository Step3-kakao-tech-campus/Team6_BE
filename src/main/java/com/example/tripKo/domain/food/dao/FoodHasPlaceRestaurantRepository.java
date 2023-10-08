package com.example.tripKo.domain.food.dao;

import com.example.tripKo.domain.food.entity.FoodHasPlaceRestaurants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodHasPlaceRestaurantRepository extends JpaRepository<FoodHasPlaceRestaurants, Long> {
}
