package com.example.tripKo.domain.place.entity;

import com.example.tripKo.BaseTimeEntity;
import com.example.tripKo.domain.place.Category;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@DynamicInsert
@DynamicUpdate
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

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Builder
    public PlaceRestaurant(Place place) {
        this.place = place;
    }
}
