package com.example.tripKo.domain.place.entity;

import com.example.tripKo.domain.BaseTimeEntity;
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
    //int -> long으로 변경
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private Integer holiday;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Builder
    public PlaceRestaurant(Place place) {
        this.place = place;
    }

    //test용
    public PlaceRestaurant(Category category, String contact_info, String openingTime, String closingTime, String breakStartTime, String breakEndTime, Integer holiday, Place place) {
        this.category = category;
        this.contact_info = contact_info;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.breakStartTime = breakStartTime;
        this.breakEndTime = breakEndTime;
        this.holiday = holiday;
        this.place = place;
    }
}
