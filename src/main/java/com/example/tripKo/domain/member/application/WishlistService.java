package com.example.tripKo.domain.member.application;

import com.example.tripKo._core.errors.exception.Exception404;
import com.example.tripKo.domain.member.dao.MemberHasPlaceIsWishedRepository;
import com.example.tripKo.domain.member.dao.MemberRepository;
import com.example.tripKo.domain.member.dto.response.wishlist.WishlistConfirmResponse;
import com.example.tripKo.domain.member.dto.response.wishlist.WishlistResponse;
import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.member.entity.MemberHasPlaceIsWished;
import com.example.tripKo.domain.place.PlaceType;
import com.example.tripKo.domain.place.dao.PlaceFestivalRepository;
import com.example.tripKo.domain.place.dao.PlaceRepository;
import com.example.tripKo.domain.place.dao.PlaceRestaurantRepository;
import com.example.tripKo.domain.place.dao.PlaceTouristSpotRepository;
import com.example.tripKo.domain.place.entity.Place;
import com.example.tripKo.domain.place.entity.PlaceRestaurant;
import com.example.tripKo.domain.place.entity.PlaceTouristSpot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishlistService {
    private final PlaceRepository placeRepository;
    private final MemberHasPlaceIsWishedRepository memberHasPlaceIsWishedRepository;
    private final PlaceRestaurantRepository placeRestaurantRepository;
    private final PlaceTouristSpotRepository placeTouristSpotRepository;
    private final PlaceFestivalRepository placeFestivalRepository;

    @Transactional
    public WishlistConfirmResponse setWishlist(Member member, Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new Exception404("해당하는 플레이스를 찾을 수 없습니다. id : " + placeId));

        boolean isWished = memberHasPlaceIsWishedRepository.existsByMemberAndPlace(member, place);
        if (isWished) {
            throw new Exception404("이미 추가되었습니다. id : " + placeId);
        }
        else {
            MemberHasPlaceIsWished memberHasPlaceIsWished = MemberHasPlaceIsWished.builder()
                    .member(member)
                    .place(place)
                    .build();

            memberHasPlaceIsWishedRepository.save(memberHasPlaceIsWished);

            WishlistConfirmResponse wishlistConfirmResponse = WishlistConfirmResponse.builder().id(memberHasPlaceIsWished.getId()).placeName(place.getName()).type(place.getPlaceType().name()).build();
            return wishlistConfirmResponse;
        }
    }

    @Transactional
    public WishlistConfirmResponse deleteWishlist(Member member, Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new Exception404("해당하는 플레이스를 찾을 수 없습니다. id : " + placeId));

        MemberHasPlaceIsWished memberHasPlaceIsWished = memberHasPlaceIsWishedRepository.findByMemberAndPlace(member, place)
                .orElseThrow(() -> new Exception404("이미 삭제되었습니다."));

        memberHasPlaceIsWishedRepository.delete(memberHasPlaceIsWished);

        WishlistConfirmResponse wishlistConfirmResponse = WishlistConfirmResponse.builder().id(memberHasPlaceIsWished.getId()).placeName(place.getName()).type(place.getPlaceType().name()).build();
        return wishlistConfirmResponse;
    }

    @Transactional
    public List<WishlistResponse> getWishlist(Member member, PlaceType placeType) {
        List<MemberHasPlaceIsWished> memberHasPlaceIsWishedList = memberHasPlaceIsWishedRepository.findAllByMemberAndPlaceType(member, placeType);
        List<WishlistResponse> wishlistResponses = null;

        switch (placeType.name()) {
            case "RESTAURANT":
                wishlistResponses = memberHasPlaceIsWishedList.stream().map(m ->
                    WishlistResponse.PlaceRestaurantBuilder().placeRestaurant(placeRestaurantRepository.findByPlace(m.getPlace())
                                    .orElseThrow(() -> new Exception404("해당하는 플레이스를 찾을 수 없습니다. id : " + m.getPlace().getId())))
                                    .PlaceRestaurantBuild()
                ).collect(Collectors.toList());
                break;
            case "FESTIVAL":
                wishlistResponses = memberHasPlaceIsWishedList.stream().map(m ->
                        WishlistResponse.PlaceFestivalBuilder().placeFestival(placeFestivalRepository.findByPlace(m.getPlace())
                                        .orElseThrow(() -> new Exception404("해당하는 플레이스를 찾을 수 없습니다. id : " + m.getPlace().getId())))
                                        .PlaceFestivalBuild()
                ).collect(Collectors.toList());
                break;
            case "TOURIST_SPOT":
                wishlistResponses = memberHasPlaceIsWishedList.stream().map(m ->
                        WishlistResponse.PlaceTouristSpotBuilder().placeTouristSpot(placeTouristSpotRepository.findByPlace(m.getPlace())
                                        .orElseThrow(() -> new Exception404("해당하는 플레이스를 찾을 수 없습니다. id : " + m.getPlace().getId())))
                                        .PlaceTouristSpotBuild()
                ).collect(Collectors.toList());
                break;
        }
        return wishlistResponses;
    }
}
