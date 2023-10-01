package com.example.tripKo.domain.place.dao;

import com.example.tripKo.domain.place.entity.PlaceTouristSpot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceTouristSpotJPARepository extends JpaRepository<PlaceTouristSpot, Long> {
    @Query("SELECT p FROM PlaceTouristSpot p WHERE UPPER(p.place.address.addressCategory.sidoName) = UPPER(:location)")
    List<PlaceTouristSpot> findAllByLocation(@Param("location") String location);

    @Query(
            value = "SELECT p FROM PlaceTouristSpot p WHERE UPPER(p.place.address.addressCategory.sidoName) = UPPER(:location)",
            countQuery = "SELECT COUNT(*) FROM PlaceTouristSpot"
    )
    Page<PlaceTouristSpot> findTouristSpotByLocation(@Param("location") String location, Pageable pageable);

}
