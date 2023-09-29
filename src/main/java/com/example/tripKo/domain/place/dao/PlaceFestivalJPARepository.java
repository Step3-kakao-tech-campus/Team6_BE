package com.example.tripKo.domain.place.dao;

import com.example.tripKo.domain.place.entity.PlaceFestival;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceFestivalJPARepository extends JpaRepository<PlaceFestival, Integer> {
    @Query(
            value = "SELECT p FROM PlaceFestival p WHERE p.place.address LIKE :location%",
            nativeQuery = true
    )
    List<PlaceFestival> findAllByLocation(@Param("location") String location);

    @Query(
            value = "SELECT p FROM PlaceFestival p WHERE p.place.address LIKE :location%",
            countQuery = "SELECT COUNT(*) FROM PlaceFestival",
            nativeQuery = true
    )
    Page<PlaceFestival> findFestivalByLocation(@Param("location") String location, Pageable pageable);
}
