package com.example.tripKo.place.restaurant;

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
@Table(name="place_restaurant")
public class PlaceRestaurant extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(length = 100)
    private String contact_info;

    @Column(length = 100)
    private String openingTime;

    @Column(length = 100)
    private String closingTime;

    @Column(length = 100)
    private String breakStartTime;

    @Column(length = 100)
    private String breakEndTime;

    @Column(length = 100)
    private String holidayDate;

    @OneToOne
    private Place place;

    @Builder
    public PlaceRestaurant(Place place) {
        this.place = place;
    }
}
