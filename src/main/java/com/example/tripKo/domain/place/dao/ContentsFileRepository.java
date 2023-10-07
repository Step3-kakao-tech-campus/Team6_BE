package com.example.tripKo.domain.place.dao;

import com.example.tripKo.domain.place.entity.ContentsHasFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentsFileRepository extends JpaRepository<ContentsHasFile, Long> {

}
