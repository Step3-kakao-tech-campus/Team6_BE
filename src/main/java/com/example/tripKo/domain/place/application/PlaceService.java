package com.example.tripKo.domain.place.application;

import com.example.tripKo._core.errors.exception.Exception404;
import com.example.tripKo.domain.place.dto.response.search.PlaceFestivalResponse;
import com.example.tripKo.domain.place.dto.response.search.PlaceResponse;
import com.example.tripKo.domain.place.dto.response.search.PlaceRestaurantResponse;
import com.example.tripKo.domain.place.dto.response.search.PlaceTouristSpotResponse;
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

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PlaceService {
    private final PlaceRestaurantRepository placeRestaurantRepository;
    private final PlaceFestivalRepository placeFestivalRepository;
    private final PlaceTouristSpotRepository placeTouristSpotRepository;

    @Transactional
    public PlaceResponse findAllByLocation(String location) {
        List<PlaceRestaurant> placeRestaurants = placeRestaurantRepository.findAllByLocation(location);
        List<PlaceFestival> placeFestivals = placeFestivalRepository.findAllByLocation(location);
        List<PlaceTouristSpot> placeTouristSpots = placeTouristSpotRepository.findAllByLocation(location);

        if(placeRestaurants.isEmpty() && placeFestivals.isEmpty() && placeTouristSpots.isEmpty()) {
            throw new Exception404("해당 지역의 정보를 찾을 수 없습니다. location : " + location);
        }

        PlaceResponse placeResponse = PlaceResponse.builder()
                                                            .placeRestaurants(placeRestaurants)
                                                            .placeFestivals(placeFestivals)
                                                            .placeTouristSpots(placeTouristSpots).build();

        return placeResponse;
    }

    @Transactional
    public List<PlaceRestaurantResponse> findRestaurantByLocation(String location, int page) {
        Pageable pageable = PageRequest.of(page,10);
        Page<PlaceRestaurant> placeRestaurants = placeRestaurantRepository.findRestaurantByLocation(location, pageable);

        if(placeRestaurants.isEmpty()) {
            throw new Exception404("해당 지역의 식당 정보를 찾을 수 없습니다. location : " + location);
        }

        List<PlaceRestaurantResponse> placeRestaurantResponses = placeRestaurants.getContent().stream().map(p-> PlaceRestaurantResponse.builder().placeRestaurant(p).build()).collect(Collectors.toList());
        return placeRestaurantResponses;
    }

    @Transactional
    public List<PlaceFestivalResponse> findFestivalByLocation(String location, int page) {
        Pageable pageable = PageRequest.of(page,10);
        Page<PlaceFestival> placeFestivals = placeFestivalRepository.findFestivalByLocation(location, pageable);

        if(placeFestivals.isEmpty()) {
            throw new Exception404("해당 지역의 식당 정보를 찾을 수 없습니다. location : " + location);
        }

        List<PlaceFestivalResponse> placeFestivalResponses = placeFestivals.getContent().stream().map(p-> PlaceFestivalResponse.builder().placeFestival(p).build()).collect(Collectors.toList());
        return placeFestivalResponses;
    }

    @Transactional
    public List<PlaceTouristSpotResponse> findTouristSpotByLocation(String location, int page) {
        Pageable pageable = PageRequest.of(page,10);
        Page<PlaceTouristSpot> placeTouristSpots = placeTouristSpotRepository.findTouristSpotByLocation(location, pageable);

        if(placeTouristSpots.isEmpty()) {
            throw new Exception404("해당 지역의 식당 정보를 찾을 수 없습니다. location : " + location);
        }

        List<PlaceTouristSpotResponse> placeTouristSpotResponses = placeTouristSpots.getContent().stream().map(p-> PlaceTouristSpotResponse.builder().placeTouristSpot(p).build()).collect(Collectors.toList());
        return placeTouristSpotResponses;
    }
}
