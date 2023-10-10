package com.example.tripKo.domain.place.entity;

import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.place.PlaceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.EnumType.STRING;

@DynamicInsert
@DynamicUpdate
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "review")
public class Review {
    @GeneratedValue(strategy = IDENTITY)
    @Column(nullable = false, updatable = false)
    @Id
    private Long id;

    @Column(nullable = false)
    private Float score;

    private String description;

    @Column(nullable = false)
    private String usage_date;

    @ManyToOne(fetch = LAZY)
    @Column(name = "member", nullable = false)
    private Member member;

    @ManyToOne(fetch = LAZY)
    @Column(name = "place", nullable = false)
    private Place place;

    @Column(nullable = false)
    private boolean isAvailable;

    @Enumerated(value = STRING)
    @Column(nullable = false)
    private PlaceType type;
}
