package com.example.tripKo.domain.food.dao;

import com.example.tripKo.domain.food.entity.Food;
import com.example.tripKo.domain.place.entity.PlaceFestival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    @Query("SELECT f FROM Food f WHERE UPPER(f.keyword) LIKE CONCAT('%', :keyword, '%')")
    List<Food> findAllByKeyword(@Param("keyword") String keyword);

}
