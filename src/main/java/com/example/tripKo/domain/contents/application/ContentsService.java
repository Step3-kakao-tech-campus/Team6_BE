package com.example.tripKo.domain.contents.application;

import com.example.tripKo.domain.contents.dao.ContentsRepository;
import com.example.tripKo.domain.contents.dto.response.FestivalResponse;
import com.example.tripKo.domain.contents.entity.Contents;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentsService {

  private final ContentsRepository contentsRepository;

  @Transactional
  public List<FestivalResponse> getFestivalInfo(long id) {
    Optional<Contents> contents = contentsRepository.findAllById(id);
    List<FestivalResponse> festivalResponses = mapContentsToResponses(contents);
    return festivalResponses;
  }

  private List<FestivalResponse> mapContentsToResponses(Optional<Contents> contents) {
    return contents.stream()
        .map(c->FestivalResponse.builder().build())
        .collect(Collectors.toList());
  }
}
