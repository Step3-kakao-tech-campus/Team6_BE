package com.example.tripKo.domain.place.dao;

import com.example.tripKo.domain.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceJPARepository extends JpaRepository<Place, Long> {
}
