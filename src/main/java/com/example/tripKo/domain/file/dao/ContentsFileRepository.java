package com.example.tripKo.domain.file.dao;

import com.example.tripKo.domain.file.entity.ContentsHasFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentsFileRepository extends JpaRepository<ContentsHasFile, Long> {

}
