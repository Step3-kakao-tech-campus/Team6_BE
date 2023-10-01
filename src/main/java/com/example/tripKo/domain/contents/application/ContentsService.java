package com.example.tripKo.domain.contents.application;

import com.example.tripKo._core.errors.exception.Exception404;
import com.example.tripKo.domain.contents.dao.ContentsRepository;
import com.example.tripKo.domain.contents.dto.response.FestivalResponse;
import com.example.tripKo.domain.contents.entity.Contents;
import com.example.tripKo.domain.place.dao.PlaceFestivalJPARepository;
import com.example.tripKo.domain.place.dao.PlaceRestaurantJPARepository;
import com.example.tripKo.domain.place.dto.PlaceRestaurantDetailsResponseDTO;
import com.example.tripKo.domain.place.entity.PlaceFestival;
import com.example.tripKo.domain.place.entity.PlaceRestaurant;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentsService {

  private final PlaceRestaurantJPARepository placeRestaurantJPARepository;
  private final PlaceFestivalJPARepository placeFestivalJPARepository;

  @Transactional
  public PlaceRestaurantDetailsResponseDTO findRestaurantDetailsById(Long id) {
        PlaceRestaurant placeRestaurant = placeRestaurantJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("해당하는 식당을 찾을 수 없습니다. id : " + id));
        PlaceRestaurantDetailsResponseDTO ResponseDTO = new PlaceRestaurantDetailsResponseDTO(placeRestaurant);
        return ResponseDTO;
  }

  @Transactional
  public FestivalResponse getFestivalInfo(Long id) {
    PlaceFestival placeFestival = placeFestivalJPARepository.findById(id)
        .orElseThrow(() -> new Exception404("해당하는 컨텐츠를 찾을 수 없습니다. id: " + id));
    FestivalResponse festivalResponse = new FestivalResponse(placeFestival);
    return festivalResponse;
  }

}
