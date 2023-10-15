package com.example.tripKo.domain.food.entity;

import com.example.tripKo.domain.place.entity.PlaceRestaurant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.FetchType.LAZY;

@DynamicInsert
@DynamicUpdate
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "food_has_place_restaurants")
public class FoodHasPlaceRestaurants {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "place_restaurant_id", nullable = false)
    private PlaceRestaurant placeRestaurant;

    @Builder
    public FoodHasPlaceRestaurants(Food food, PlaceRestaurant placeRestaurant) {
        this.food = food;
        this.placeRestaurant = placeRestaurant;
    }
}
