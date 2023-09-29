package com.example.tripKo.domain.file.dao;

import com.example.tripKo.domain.file.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {

}
