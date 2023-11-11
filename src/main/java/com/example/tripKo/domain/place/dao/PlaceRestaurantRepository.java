package com.example.tripKo.domain.place.dao;

import com.example.tripKo.domain.place.entity.Place;
import com.example.tripKo.domain.place.entity.PlaceFestival;
import com.example.tripKo.domain.place.entity.PlaceRestaurant;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceRestaurantRepository extends JpaRepository<PlaceRestaurant, Long> {

  @Query("SELECT p FROM PlaceRestaurant p WHERE UPPER(p.place.address.addressCategory.sidoName) = UPPER(:location)")
  List<PlaceRestaurant> findAllByLocation(@Param("location") String location);

  @Query(
      value = "SELECT p FROM PlaceRestaurant p WHERE UPPER(p.place.address.addressCategory.sidoName) = UPPER(:location)",
      countQuery = "SELECT COUNT(*) FROM PlaceRestaurant"
  )
  Page<PlaceRestaurant> findRestaurantByLocation(@Param("location") String location, Pageable pageable);

  @Query("SELECT DISTINCT r FROM PlaceRestaurant r " +
      "JOIN FETCH r.place p " +
      "LEFT JOIN FETCH p.file f " +
      "JOIN FETCH p.address a " +
      "JOIN FETCH a.addressCategory ac " +
      "JOIN FETCH p.contents c " +
//            "LEFT JOIN FETCH c.contentsMenus m " +
//            "LEFT JOIN FETCH c.contentsFiles cf " +
      "WHERE r.id = :id")
  PlaceRestaurant findRestaurantDetailsById(@Param("id") Long id);

  Optional<PlaceRestaurant> findByIdAndIdNot(Long id, Long virtualId);

  Optional<PlaceRestaurant> findByPlaceId(Long placeId);
  Optional<PlaceRestaurant> findByPlace(Place place);

  List<PlaceRestaurant> findTop5ByOrderByPlace_AverageRatingDesc();

}
