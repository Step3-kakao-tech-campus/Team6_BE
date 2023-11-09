package com.example.tripKo.domain.place.application;

import com.example.tripKo._core.errors.exception.Exception404;
import com.example.tripKo.domain.member.dao.MemberRepository;
import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.place.dto.response.search.PlaceFestivalResponse;
import com.example.tripKo.domain.place.dto.response.search.PlaceResponse;
import com.example.tripKo.domain.place.dto.response.search.PlaceRestaurantResponse;
import com.example.tripKo.domain.place.dto.response.search.PlaceTouristSpotResponse;
import com.example.tripKo.domain.place.entity.Place;
import com.example.tripKo.domain.place.entity.PlaceFestival;
import com.example.tripKo.domain.place.dao.PlaceFestivalRepository;
import com.example.tripKo.domain.place.entity.PlaceRestaurant;
import com.example.tripKo.domain.place.dao.PlaceRestaurantRepository;
import com.example.tripKo.domain.place.entity.PlaceTouristSpot;
import com.example.tripKo.domain.place.dao.PlaceTouristSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PlaceService {

  private final PlaceRestaurantRepository placeRestaurantRepository;
  private final PlaceFestivalRepository placeFestivalRepository;
  private final PlaceTouristSpotRepository placeTouristSpotRepository;
  private final MemberRepository memberRepository;

  @Transactional
  public PlaceResponse findAllByLocation(String location, Long id) {
    List<PlaceRestaurant> placeRestaurants = placeRestaurantRepository.findAllByLocation(location);
    List<PlaceFestival> placeFestivals = placeFestivalRepository.findAllByLocation(location);
    List<PlaceTouristSpot> placeTouristSpots = placeTouristSpotRepository.findAllByLocation(location);

    if (placeRestaurants.isEmpty() && placeFestivals.isEmpty() && placeTouristSpots.isEmpty()) {
      throw new Exception404("해당 지역의 정보를 찾을 수 없습니다. location : " + location);
    }

    List<Place> places = new ArrayList<>();
    // AuthenticationPrincipal을 통해서 접근하는 member 객체가 준영속 상태이므로 MemberHasPlaceIsWishedList를 Lazy fetch를 통해 가져올 수 없음
    // 따라서 id만 받아서 다시 memberRepository에서 검색해서 가져오는 방식으로 구현
    // 참고 https://velog.io/@skrek269/%EC%97%90%EB%9F%AC-%EC%B2%98%EB%A6%AC-%EA%B8%B0%EB%A1%9D-org.hibernate.LazyInitializationException
    if (id != null) {
      Member member = memberRepository.findById(id).orElseThrow();
      places = member.getMemberHasPlaceIsWishedList().stream().map(p -> p.getPlace()).collect(Collectors.toList());
    }
    PlaceResponse placeResponse = PlaceResponse.builder()
        .placeRestaurants(placeRestaurants)
        .placeFestivals(placeFestivals)
        .placeTouristSpots(placeTouristSpots)
        .places(places)
        .build();

    return placeResponse;
  }

  @Transactional
  public List<PlaceRestaurantResponse> findRestaurantByLocation(String location, int page, Long id) {
    Pageable pageable = PageRequest.of(page, 10);
    Page<PlaceRestaurant> placeRestaurants = placeRestaurantRepository.findRestaurantByLocation(location, pageable);

    if (placeRestaurants.isEmpty()) {
      throw new Exception404("해당 지역의 식당 정보를 찾을 수 없습니다. location : " + location);
    }

    List<Place> places = new ArrayList<>();
    // AuthenticationPrincipal을 통해서 접근하는 member 객체가 준영속 상태이므로 MemberHasPlaceIsWishedList를 Lazy fetch를 통해 가져올 수 없음
    // 따라서 id만 받아서 다시 memberRepository에서 검색해서 가져오는 방식으로 구현
    // 참고 https://velog.io/@skrek269/%EC%97%90%EB%9F%AC-%EC%B2%98%EB%A6%AC-%EA%B8%B0%EB%A1%9D-org.hibernate.LazyInitializationException
    if (id != null) {
      Member member = memberRepository.findById(id).orElseThrow();
      places = member.getMemberHasPlaceIsWishedList().stream().map(p -> p.getPlace()).collect(Collectors.toList());
    }

    List<PlaceRestaurantResponse> placeRestaurantResponses = new ArrayList<>();
    for (PlaceRestaurant p : placeRestaurants) {
      placeRestaurantResponses.add(
          PlaceRestaurantResponse.builder().placeRestaurant(p).isWished(places.contains(p.getPlace())).build());
    }
    return placeRestaurantResponses;
  }

  @Transactional
  public List<PlaceFestivalResponse> findFestivalByLocation(String location, int page, Long id) {
    Pageable pageable = PageRequest.of(page, 10);
    Page<PlaceFestival> placeFestivals = placeFestivalRepository.findFestivalByLocation(location, pageable);

    if (placeFestivals.isEmpty()) {
      throw new Exception404("해당 지역의 식당 정보를 찾을 수 없습니다. location : " + location);
    }

    List<Place> places = new ArrayList<>();
    // AuthenticationPrincipal을 통해서 접근하는 member 객체가 준영속 상태이므로 MemberHasPlaceIsWishedList를 Lazy fetch를 통해 가져올 수 없음
    // 따라서 id만 받아서 다시 memberRepository에서 검색해서 가져오는 방식으로 구현
    // 참고 https://velog.io/@skrek269/%EC%97%90%EB%9F%AC-%EC%B2%98%EB%A6%AC-%EA%B8%B0%EB%A1%9D-org.hibernate.LazyInitializationException
    if (id != null) {
      Member member = memberRepository.findById(id).orElseThrow();
      places = member.getMemberHasPlaceIsWishedList().stream().map(p -> p.getPlace()).collect(Collectors.toList());
    }

    List<PlaceFestivalResponse> placeFestivalResponses = new ArrayList<>();
    for (PlaceFestival p : placeFestivals) {
      placeFestivalResponses.add(
          PlaceFestivalResponse.builder().placeFestival(p).isWished(places.contains(p.getPlace())).build());
    }
    return placeFestivalResponses;
  }

  @Transactional
  public List<PlaceTouristSpotResponse> findTouristSpotByLocation(String location, int page, Long id) {
    Pageable pageable = PageRequest.of(page, 10);
    Page<PlaceTouristSpot> placeTouristSpots = placeTouristSpotRepository.findTouristSpotByLocation(location, pageable);

    if (placeTouristSpots.isEmpty()) {
      throw new Exception404("해당 지역의 식당 정보를 찾을 수 없습니다. location : " + location);
    }

    List<Place> places = new ArrayList<>();
    // AuthenticationPrincipal을 통해서 접근하는 member 객체가 준영속 상태이므로 MemberHasPlaceIsWishedList를 Lazy fetch를 통해 가져올 수 없음
    // 따라서 id만 받아서 다시 memberRepository에서 검색해서 가져오는 방식으로 구현
    // 참고 https://velog.io/@skrek269/%EC%97%90%EB%9F%AC-%EC%B2%98%EB%A6%AC-%EA%B8%B0%EB%A1%9D-org.hibernate.LazyInitializationException
    if (id != null) {
      Member member = memberRepository.findById(id).orElseThrow();
      places = member.getMemberHasPlaceIsWishedList().stream().map(p -> p.getPlace()).collect(Collectors.toList());
    }

    List<PlaceTouristSpotResponse> placeTouristSpotResponses = new ArrayList<>();
    for (PlaceTouristSpot p : placeTouristSpots) {
      placeTouristSpotResponses.add(
          PlaceTouristSpotResponse.builder().placeTouristSpot(p).isWished(places.contains(p.getPlace())).build());
    }
    return placeTouristSpotResponses;
  }
}
