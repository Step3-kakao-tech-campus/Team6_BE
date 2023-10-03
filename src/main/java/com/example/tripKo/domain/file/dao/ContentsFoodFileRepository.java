package com.example.tripKo.domain.file.dao;

import com.example.tripKo.domain.file.entity.ContentsFile;
import com.example.tripKo.domain.file.entity.ContentsFoodFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentsFoodFileRepository extends JpaRepository<ContentsFoodFile, Long> {

}
