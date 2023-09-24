package com.example.tripKo.place;

import com.example.tripKo.place.festival.PlaceFestival;
import com.example.tripKo.place.festival.PlaceFestivalJPARepository;
import com.example.tripKo.place.restaurant.PlaceRestaurant;
import com.example.tripKo.place.restaurant.PlaceRestaurantJPARepository;
import com.example.tripKo.place.touristSpot.PlaceTouristSpot;
import com.example.tripKo.place.touristSpot.PlaceTouristSpotJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
