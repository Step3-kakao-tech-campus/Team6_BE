package com.example.tripKo.domain.place.dao;

import com.example.tripKo.domain.place.entity.Place;
import com.example.tripKo.domain.place.entity.PlaceFestival;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceFestivalRepository extends JpaRepository<PlaceFestival, Long> {

  @Query("SELECT p FROM PlaceFestival p WHERE UPPER(p.place.address.addressCategory.sidoName) = UPPER(:location)")
  List<PlaceFestival> findAllByLocation(@Param("location") String location);

  @Query(
      value = "SELECT p FROM PlaceFestival p WHERE UPPER(p.place.address.addressCategory.sidoName) = UPPER(:location)",
      countQuery = "SELECT COUNT(*) FROM PlaceFestival"
  )
  Page<PlaceFestival> findFestivalByLocation(@Param("location") String location, Pageable pageable);

  Optional<PlaceFestival> findByIdAndIdNot(Long id, Long virtualId);

  Optional<PlaceFestival> findByPlaceId(Long placeId);

  Optional<PlaceFestival> findByPlace(Place place);
}
