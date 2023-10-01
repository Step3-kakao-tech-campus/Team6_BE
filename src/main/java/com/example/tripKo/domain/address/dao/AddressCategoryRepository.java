package com.example.tripKo.domain.address.dao;

import com.example.tripKo.domain.address.entity.AddressCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressCategoryRepository extends JpaRepository<AddressCategory, Long> {
}
