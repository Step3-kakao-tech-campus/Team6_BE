package com.example.tripKo.place;

import com.example.tripKo.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="place")
public class Place extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 1000, nullable = false)
    private String summary;

    private int count;
    private float averageRating;

//    private File file;
    @Column(length = 1000, nullable = false)
    private String address;

    @Builder
    public Place(String name, String summary, int count, float averageRating, String address) {
        this.name = name;
        this.summary = summary;
        this.count = count;
        this.averageRating = averageRating;
        this.address = address;
    }
}
