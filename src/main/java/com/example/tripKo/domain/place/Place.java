package com.example.tripKo.domain.place;

import com.example.tripKo.BaseTimeEntity;
import com.example.tripKo.domain.Address;
import com.example.tripKo.domain.File;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

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

    @Column
    private int count;

    @Column
    private float averageRating;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private File file;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Builder
    public Place(String name, String summary, int count, float averageRating, Address address) {
        this.name = name;
        this.summary = summary;
        this.count = count;
        this.averageRating = averageRating;
        this.address = address;
    }
}
