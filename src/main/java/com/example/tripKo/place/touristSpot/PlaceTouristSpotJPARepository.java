package com.example.tripKo.place.touristSpot;

import com.example.tripKo.place.Place;
import com.example.tripKo.place.restaurant.PlaceRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceTouristSpotJPARepository extends JpaRepository<PlaceTouristSpot, Integer> {
    @Query("SELECT p FROM PlaceTouristSpot p WHERE p.place.address LIKE :location%")
    List<PlaceTouristSpot> findAllByLocation(@Param("location") String location);

}
