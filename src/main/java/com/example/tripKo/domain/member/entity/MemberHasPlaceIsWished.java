package com.example.tripKo.domain.member.entity;

import com.example.tripKo.domain.place.entity.Place;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@DynamicInsert
@DynamicUpdate
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "member_has_place_isWished")
public class MemberHasPlaceIsWished {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Builder
    public MemberHasPlaceIsWished(Member member, Place place) {
        this.member = member;
        this.place = place;
    }
}
