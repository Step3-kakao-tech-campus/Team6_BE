package com.example.tripKo.domain.place.dto.request;


import com.sun.istack.NotNull;
import lombok.Getter;

public class RestaurantReservationRequest {

    @Getter
    public static class SelectDTO {
        @NotNull
        private Long id;
    }

    @Getter
    public static class ResultDTO {
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
        @NotNull
        private Long memberId;
        private String message;
    }
}
