package com.example.tripKo.place.festival;

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
@Table(name="place_festival")
public class PlaceFestival extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false)
    private String startDate;

    @Column(length = 100, nullable = false)
    private String endDate;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToOne
    private Place place;

    @Column(nullable = false)
    private Boolean reservationAvailable;

    @Builder
    public PlaceFestival(String startDate, String endDate, Place place, Boolean reservationAvailable) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.place = place;
        this.reservationAvailable = reservationAvailable;
    }
}
