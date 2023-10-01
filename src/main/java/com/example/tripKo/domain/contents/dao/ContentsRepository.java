package com.example.tripKo.domain.contents.dao;

import com.example.tripKo.domain.contents.entity.Contents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentsRepository extends JpaRepository<Contents, Long> {

//  Optional<Contents> findByIdAndIdNot(Long id, Long virtualId);

}
