package com.example.tripKo.domain.place.dto.request;


import com.sun.istack.NotNull;
import lombok.Getter;

@Getter
public class RestaurantReservationSelectRequest {
    @NotNull
    private Long id;
}
