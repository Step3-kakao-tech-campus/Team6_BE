package com.example.tripKo.domain.contents.application;

import com.example.tripKo._core.errors.exception.Exception404;
import com.example.tripKo.domain.contents.dao.ContentsRepository;
import com.example.tripKo.domain.contents.dto.response.FestivalResponse;
import com.example.tripKo.domain.contents.entity.Contents;
import com.example.tripKo.domain.place.dao.PlaceRestaurantJPARepository;
import com.example.tripKo.domain.place.dto.PlaceRestaurantDetailsResponseDTO;
import com.example.tripKo.domain.place.entity.PlaceRestaurant;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentsService {

  private final ContentsRepository contentsRepository;
  private final PlaceRestaurantJPARepository placeRestaurantJPARepository;

  @Transactional
  public PlaceRestaurantDetailsResponseDTO findRestaurantDetailsById(Long id) {
        PlaceRestaurant placeRestaurant = placeRestaurantJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("해당하는 식당을 찾을 수 없습니다. id : " + id));
        PlaceRestaurantDetailsResponseDTO ResponseDTO = new PlaceRestaurantDetailsResponseDTO(placeRestaurant);
        return ResponseDTO;
  }

  @Transactional
  public FestivalResponse getFestivalInfo(long id) {
    Optional<Contents> contents = contentsRepository.findById(id);
    return contents.map(FestivalResponse::from)
        .orElse(null);
  }

}
