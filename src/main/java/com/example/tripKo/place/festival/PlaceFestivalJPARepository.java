package com.example.tripKo.place.festival;

import com.example.tripKo.place.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceFestivalJPARepository extends JpaRepository<PlaceFestival, Integer> {
    @Query("SELECT p FROM PlaceFestival p WHERE p.place.address LIKE :location%")
    List<PlaceFestival> findAllByLocation(@Param("location") String location);
}
