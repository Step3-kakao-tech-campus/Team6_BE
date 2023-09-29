package com.example.tripKo.domain.place.application;

import com.example.tripKo.domain.place.dto.PlaceResponseDTO;
import com.example.tripKo.domain.place.entity.PlaceFestival;
import com.example.tripKo.domain.place.dao.PlaceFestivalJPARepository;
import com.example.tripKo.domain.place.dto.PlaceFestivalResponseDTO;
import com.example.tripKo.domain.place.entity.PlaceRestaurant;
import com.example.tripKo.domain.place.dao.PlaceRestaurantJPARepository;
import com.example.tripKo.domain.place.dto.PlaceRestaurantResponseDTO;
import com.example.tripKo.domain.place.entity.PlaceTouristSpot;
import com.example.tripKo.domain.place.dao.PlaceTouristSpotJPARepository;
import com.example.tripKo.domain.place.dto.PlaceTouristSpotResponseDTO;
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
    private final PlaceRestaurantJPARepository placeRestaurantJPARepository;
    private final PlaceFestivalJPARepository placeFestivalJPARepository;
    private final PlaceTouristSpotJPARepository placeTouristSpotJPARepository;
    @Transactional
    public PlaceResponseDTO findAllByLocation(String location) {
//        Place tmp = Place.builder().name("맛도리 짜장").count(10).averageRating(4.1f).summary("개맛도리 추배릅").address("Busan sahagu").build();
//        placeJPARepository.saveAndFlush(tmp);
//        placeRestaurantJPARepository.saveAndFlush(PlaceRestaurant.builder().place(tmp).build());
//        placeFestivalJPARepository.saveAndFlush(PlaceFestival.builder().endDate("20-12-25").startDate("20-09-20").reservationAvailable(true).place(tmp).build());
//        placeTouristSpotJPARepository.saveAndFlush(PlaceTouristSpot.builder().place(tmp).build());
        List<PlaceRestaurant> placeRestaurants = placeRestaurantJPARepository.findAllByLocation(location);
        List<PlaceFestival> placeFestivals = placeFestivalJPARepository.findAllByLocation(location);
        List<PlaceTouristSpot> placeTouristSpots = placeTouristSpotJPARepository.findAllByLocation(location);
        PlaceResponseDTO placeResponseDTO = PlaceResponseDTO.builder()
                                                            .placeRestaurants(placeRestaurants)
                                                            .placeFestivals(placeFestivals)
                                                            .placeTouristSpots(placeTouristSpots).build();
        return placeResponseDTO;
    }

    @Transactional
    public List<PlaceRestaurantResponseDTO> findRestaurantByLocation(String location, int page) {
        Pageable pageable = PageRequest.of(page,10);
        Page<PlaceRestaurant> placeRestaurants = placeRestaurantJPARepository.findRestaurantByLocation(location, pageable);
        List<PlaceRestaurantResponseDTO> placeRestaurantResponseDTOs = placeRestaurants.getContent().stream().map(p->PlaceRestaurantResponseDTO.builder().placeRestaurant(p).build()).collect(Collectors.toList());
        return placeRestaurantResponseDTOs;
    }

    @Transactional
    public List<PlaceFestivalResponseDTO> findFestivalByLocation(String location, int page) {
        Pageable pageable = PageRequest.of(page,10);
        Page<PlaceFestival> placeFestivals = placeFestivalJPARepository.findFestivalByLocation(location, pageable);
        List<PlaceFestivalResponseDTO> placeFestivalResponseDTOS = placeFestivals.getContent().stream().map(p-> PlaceFestivalResponseDTO.builder().placeFestival(p).build()).collect(Collectors.toList());
        return placeFestivalResponseDTOS;
    }

    @Transactional
    public List<PlaceTouristSpotResponseDTO> findTouristSpotByLocation(String location, int page) {
        Pageable pageable = PageRequest.of(page,10);
        Page<PlaceTouristSpot> placeTouristSpots = placeTouristSpotJPARepository.findTouristSpotByLocation(location, pageable);
        List<PlaceTouristSpotResponseDTO> placeTouristSpotResponseDTOS = placeTouristSpots.getContent().stream().map(p-> PlaceTouristSpotResponseDTO.builder().placeTouristSpot(p).build()).collect(Collectors.toList());
        return placeTouristSpotResponseDTOS;
    }
}
