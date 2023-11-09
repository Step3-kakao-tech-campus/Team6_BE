package com.example.tripKo.domain.place.dto.response.info;


import com.example.tripKo.domain.place.entity.PlaceFestival;
import com.example.tripKo.domain.place.entity.PlaceRestaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;


@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
public class FestivalReservationSelectResponse {
    private String startDate;
    private String endDate;
    private boolean reservationAvailable;

    public FestivalReservationSelectResponse (PlaceFestival placeFestival) {
        this.startDate = placeFestival.getStartDate();
        this.endDate = placeFestival.getEndDate();
        this.reservationAvailable = placeFestival.getReservationAvailable();
    }
}
