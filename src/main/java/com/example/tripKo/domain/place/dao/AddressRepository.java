package com.example.tripKo.domain.place.dao;

import com.example.tripKo.domain.place.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
