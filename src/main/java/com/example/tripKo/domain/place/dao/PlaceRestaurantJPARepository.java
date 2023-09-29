package com.example.tripKo.domain.place.dao;

import com.example.tripKo.domain.place.entity.PlaceRestaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceRestaurantJPARepository extends JpaRepository<PlaceRestaurant, Integer> {
    @Query(
            value = "SELECT p FROM PlaceRestaurant p WHERE p.place.address LIKE :location%",
            nativeQuery = true
    )
    List<PlaceRestaurant> findAllByLocation(@Param("location") String location);

    @Query(
            value = "SELECT p FROM PlaceRestaurant p WHERE p.place.address LIKE :location%",
            countQuery = "SELECT COUNT(*) FROM PlaceRestaurant",
            nativeQuery = true
    )
    Page<PlaceRestaurant> findRestaurantByLocation(@Param("location") String location, Pageable pageable);

}
