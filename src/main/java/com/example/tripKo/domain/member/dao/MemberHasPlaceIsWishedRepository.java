package com.example.tripKo.domain.member.dao;

import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.member.entity.MemberHasPlaceIsWished;
import com.example.tripKo.domain.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberHasPlaceIsWishedRepository  extends JpaRepository<MemberHasPlaceIsWished, Long> {
    Boolean ExistsByMemberAndPlace(Member member, Place place);
}
