package com.example.tripKo.domain.place.dao;

import com.example.tripKo.domain.place.entity.AddressCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressCategoryRepository extends JpaRepository<AddressCategory, Long> {
}
