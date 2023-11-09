package com.example.tripKo.domain.place.application;

import com.example.tripKo._core.errors.exception.Exception404;
import com.example.tripKo.domain.member.dao.MemberRepository;
import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.place.application.convenience.ValidContentsService;
import com.example.tripKo.domain.place.dao.PlaceFestivalRepository;
import com.example.tripKo.domain.place.dao.PlaceRestaurantRepository;
import com.example.tripKo.domain.place.dao.PlaceTouristSpotRepository;
import com.example.tripKo.domain.place.dto.response.info.FestivalResponse;
import com.example.tripKo.domain.place.dto.response.info.HomeResponse;
import com.example.tripKo.domain.place.dto.response.info.TouristSpotResponse;
import com.example.tripKo.domain.place.dto.response.info.RestaurantResponse;
import com.example.tripKo.domain.place.entity.Place;
import com.example.tripKo.domain.place.entity.PlaceFestival;
import com.example.tripKo.domain.place.entity.PlaceRestaurant;
import com.example.tripKo.domain.place.entity.PlaceTouristSpot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentsService {

  private final ValidContentsService validContentsService;
  private final PlaceRestaurantRepository placeRestaurantRepository;
  private final PlaceFestivalRepository placeFestivalRepository;
  private final PlaceTouristSpotRepository placeTouristSpotRepository;
  private final MemberRepository memberRepository;

  @Transactional
  public RestaurantResponse getRestaurantInfo(Long placeId, Long memberId) {
    List<Place> places = new ArrayList<>();
    // AuthenticationPrincipal을 통해서 접근하는 member 객체가 준영속 상태이므로 MemberHasPlaceIsWishedList를 Lazy fetch를 통해 가져올 수 없음
    // 따라서 id만 받아서 다시 memberRepository에서 검색해서 가져오는 방식으로 구현
    // 참고 https://velog.io/@skrek269/%EC%97%90%EB%9F%AC-%EC%B2%98%EB%A6%AC-%EA%B8%B0%EB%A1%9D-org.hibernate.LazyInitializationException
    if (memberId != null) {
      Member member = memberRepository.findById(memberId).orElseThrow();
      places = member.getMemberHasPlaceIsWishedList().stream().map(p -> p.getPlace()).collect(Collectors.toList());
    }

    PlaceRestaurant placeRestaurant = placeRestaurantRepository.findByPlaceId(placeId)
        .orElseThrow(() -> new Exception404("해당 식당을 찾을 수 없습니다. id : " + placeId));
//    PlaceRestaurant placeRestaurant = validContentsService.findByRestaurantId(placeId);
    return RestaurantResponse.from(placeRestaurant, places.contains(placeRestaurant.getPlace()));
  }

  @Transactional
  public FestivalResponse getFestivalInfo(Long placeId, Long memberId) {
    List<Place> places = new ArrayList<>();
    // AuthenticationPrincipal을 통해서 접근하는 member 객체가 준영속 상태이므로 MemberHasPlaceIsWishedList를 Lazy fetch를 통해 가져올 수 없음
    // 따라서 id만 받아서 다시 memberRepository에서 검색해서 가져오는 방식으로 구현
    // 참고 https://velog.io/@skrek269/%EC%97%90%EB%9F%AC-%EC%B2%98%EB%A6%AC-%EA%B8%B0%EB%A1%9D-org.hibernate.LazyInitializationException
    if (memberId != null) {
      Member member = memberRepository.findById(memberId).orElseThrow();
      places = member.getMemberHasPlaceIsWishedList().stream().map(p -> p.getPlace()).collect(Collectors.toList());
    }

    PlaceFestival placeFestival = placeFestivalRepository.findByPlaceId(placeId)
        .orElseThrow(() -> new Exception404("해당 식당을 찾을 수 없습니다. id : " + placeId));
//    PlaceFestival placeFestival = validContentsService.findByFestivalId(placeId);
    return FestivalResponse.from(placeFestival, places.contains(placeFestival.getPlace()));
  }

  @Transactional
  public TouristSpotResponse getTouristSpotInfo(Long placeId, Long memberId) {
    List<Place> places = new ArrayList<>();
    // AuthenticationPrincipal을 통해서 접근하는 member 객체가 준영속 상태이므로 MemberHasPlaceIsWishedList를 Lazy fetch를 통해 가져올 수 없음
    // 따라서 id만 받아서 다시 memberRepository에서 검색해서 가져오는 방식으로 구현
    // 참고 https://velog.io/@skrek269/%EC%97%90%EB%9F%AC-%EC%B2%98%EB%A6%AC-%EA%B8%B0%EB%A1%9D-org.hibernate.LazyInitializationException
    if (memberId != null) {
      Member member = memberRepository.findById(memberId).orElseThrow();
      places = member.getMemberHasPlaceIsWishedList().stream().map(p -> p.getPlace()).collect(Collectors.toList());
    }

    PlaceTouristSpot placeTouristSpot = placeTouristSpotRepository.findByPlaceId(placeId)
        .orElseThrow(() -> new Exception404("해당 식당을 찾을 수 없습니다. id : " + placeId));
//    PlaceTouristSpot placeTouristSpot = validContentsService.findByTouristSpotId(placeId);
    return TouristSpotResponse.from(placeTouristSpot, places.contains(placeTouristSpot.getPlace()));
  }

  @Transactional
  public HomeResponse getHomeInfo(Long memberId) {
    List<Place> places = new ArrayList<>();
    // AuthenticationPrincipal을 통해서 접근하는 member 객체가 준영속 상태이므로 MemberHasPlaceIsWishedList를 Lazy fetch를 통해 가져올 수 없음
    // 따라서 id만 받아서 다시 memberRepository에서 검색해서 가져오는 방식으로 구현
    // 참고 https://velog.io/@skrek269/%EC%97%90%EB%9F%AC-%EC%B2%98%EB%A6%AC-%EA%B8%B0%EB%A1%9D-org.hibernate.LazyInitializationException
    if (memberId != null) {
      Member member = memberRepository.findById(memberId).orElseThrow();
      places = member.getMemberHasPlaceIsWishedList().stream().map(p -> p.getPlace()).collect(Collectors.toList());
    }

    List<PlaceRestaurant> placeRestaurants = placeRestaurantRepository.findAll();
    List<RestaurantResponse> restaurantResponses = new ArrayList<>();
    for (PlaceRestaurant p : placeRestaurants) {
      restaurantResponses.add(RestaurantResponse.from(p, places.contains(p.getPlace())));
    }

    List<PlaceFestival> placeFestivals = placeFestivalRepository.findAll();
    List<FestivalResponse> festivalResponses = new ArrayList<>();
    for (PlaceFestival p : placeFestivals) {
      festivalResponses.add(FestivalResponse.from(p, places.contains(p.getPlace())));
    }

    List<PlaceTouristSpot> placeTouristSpots = placeTouristSpotRepository.findAll();
    List<TouristSpotResponse> touristSpotResponses = new ArrayList<>();
    for (PlaceTouristSpot p : placeTouristSpots) {
      touristSpotResponses.add(TouristSpotResponse.from(p, places.contains(p.getPlace())));
    }

    return HomeResponse.from(restaurantResponses, festivalResponses, touristSpotResponses);
  }

}
