package com.example.tripKo.place.touristSpot;

import com.example.tripKo.BaseTimeEntity;
import com.example.tripKo.place.Category;
import com.example.tripKo.place.Place;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="place_tourist_spot")
public class PlaceTouristSpot extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToOne
    private Place place;

    @Builder
    public PlaceTouristSpot(Place place) {
        this.place = place;
    }
}
