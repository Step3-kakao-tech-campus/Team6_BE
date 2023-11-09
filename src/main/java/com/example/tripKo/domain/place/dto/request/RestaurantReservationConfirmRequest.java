package com.example.tripKo.domain.place.dto.request;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RestaurantReservationConfirmRequest {

    private Reservation reservation;

    @Getter
    public static class Reservation {
        @NotNull
        private Long id;
        @NotNull
        private Long placeId;
        @NotNull
        private String reservationDate;
        @NotNull
        private String reservationTime;
        @NotNull
        private Long headCount;
        private String message;
    }
}
