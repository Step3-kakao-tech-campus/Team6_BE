package com.example.tripKo.domain.member.dao;

import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.member.entity.MemberHasPlaceIsWished;
import com.example.tripKo.domain.place.PlaceType;
import com.example.tripKo.domain.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberHasPlaceIsWishedRepository  extends JpaRepository<MemberHasPlaceIsWished, Long> {
    Boolean existsByMemberAndPlace(Member member, Place place);

    Optional<MemberHasPlaceIsWished> findByMemberAndPlace(Member member, Place place);

    @Query("select m from MemberHasPlaceIsWished m where m.member = :member and m.place.placeType = :placeType")
    List<MemberHasPlaceIsWished> findAllByMemberAndPlaceType(Member member, PlaceType placeType);
}
