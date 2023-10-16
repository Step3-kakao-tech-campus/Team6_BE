package com.example.tripKo.domain.place.dto.response.info;


import com.example.tripKo.domain.place.entity.PlaceRestaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;


@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
public class RestaurantReservationSelectResponse {
    private String holidayDate;
    private String reservationAvailableStartTime;
    private String reservationAvailableEndTime;
    private String breakStartTime;
    private String breakEndTime;

    public RestaurantReservationSelectResponse (PlaceRestaurant placeRestaurant) {
        this.holidayDate = placeRestaurant.getHolidayDate();
        this.reservationAvailableStartTime = placeRestaurant.getOpeningTime();
        this.reservationAvailableEndTime = placeRestaurant.getClosingTime();
        this.breakStartTime = placeRestaurant.getBreakStartTime();
        this.breakEndTime = placeRestaurant.getBreakEndTime();
    }
}
