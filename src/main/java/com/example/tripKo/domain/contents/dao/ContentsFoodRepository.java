package com.example.tripKo.domain.contents.dao;

import com.example.tripKo.domain.contents.entity.Contents;
import com.example.tripKo.domain.contents.entity.ContentsFood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentsFoodRepository  extends JpaRepository<ContentsFood, Long> {
}
