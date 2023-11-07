package com.example.tripKo.domain.member.application;

import com.example.tripKo._core.errors.exception.Exception400;
import com.example.tripKo._core.errors.exception.Exception404;
import com.example.tripKo.domain.member.dao.MemberHasPlaceIsWishedRepository;
import com.example.tripKo.domain.member.dao.MemberRepository;
import com.example.tripKo.domain.member.dto.response.WishlistResponse;
import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.member.entity.MemberHasPlaceIsWished;
import com.example.tripKo.domain.place.dao.PlaceRepository;
import com.example.tripKo.domain.place.entity.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishlistService {
    private final MemberRepository memberRepository;
    private final PlaceRepository placeRepository;
    private final MemberHasPlaceIsWishedRepository memberHasPlaceIsWishedRepository;
    @Transactional
    public WishlistResponse setWishlist(Member member, Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new Exception400("해당하는 플레이스를 찾을 수 없습니다. id : " + placeId));

        boolean isWished = memberHasPlaceIsWishedRepository.ExistsByMemberAndPlace(member, place);
        if (isWished) {
            throw new Exception400("이미 추가되었습니다. id : " + placeId);
        }
        else {
            MemberHasPlaceIsWished memberHasPlaceIsWished = MemberHasPlaceIsWished.builder()
                    .member(member)
                    .place(place)
                    .build();

            WishlistResponse wishlistResponse = WishlistResponse.builder().id(memberHasPlaceIsWished.getId()).placeName(place.getName()).build();
            return wishlistResponse;
        }
    }
}
