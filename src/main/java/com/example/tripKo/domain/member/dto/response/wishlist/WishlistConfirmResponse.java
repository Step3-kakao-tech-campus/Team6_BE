package com.example.tripKo.domain.member.dto.response.wishlist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
public class WishlistConfirmResponse {
    private Long id;
    private String placeName;
    private String type;
}
