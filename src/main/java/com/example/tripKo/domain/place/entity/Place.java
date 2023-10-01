package com.example.tripKo.domain.place.entity;

import com.example.tripKo.domain.BaseTimeEntity;
import com.example.tripKo.domain.address.entity.Address;
import com.example.tripKo.domain.contents.entity.Contents;
import com.example.tripKo.domain.file.entity.File;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(fetch = LAZY, mappedBy = "place")
    private final List<Contents> contents = new ArrayList<>();

    @Builder
    public Place(String name, String summary, int count, float averageRating, File file, Address address) {
        this.name = name;
        this.summary = summary;
        this.count = count;
        this.averageRating = averageRating;
        this.file = file;
        this.address = address;
    }
}
