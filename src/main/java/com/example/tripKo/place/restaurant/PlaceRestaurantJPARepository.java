package com.example.tripKo.place.restaurant;

import com.example.tripKo.place.festival.PlaceFestival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceRestaurantJPARepository extends JpaRepository<PlaceRestaurant, Integer> {
    @Query("SELECT p FROM PlaceRestaurant p WHERE p.place.address LIKE :location%")
    List<PlaceRestaurant> findAllByLocation(@Param("location") String location);

}
