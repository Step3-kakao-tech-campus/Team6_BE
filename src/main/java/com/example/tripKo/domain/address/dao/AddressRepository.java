package com.example.tripKo.domain.address.dao;

import com.example.tripKo.domain.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
