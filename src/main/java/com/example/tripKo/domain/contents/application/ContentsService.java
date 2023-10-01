package com.example.tripKo.domain.contents.application;

import com.example.tripKo.domain.contents.dao.ContentsRepository;
import com.example.tripKo.domain.contents.dto.response.FestivalResponse;
import com.example.tripKo.domain.contents.entity.Contents;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentsService {

  private final ContentsRepository contentsRepository;

  @Transactional
  public FestivalResponse getFestivalInfo(long id) {
    Optional<Contents> contents = contentsRepository.findFestivalById(id);
    return contents.map(FestivalResponse::from)
        .orElse(null);
  }

}
