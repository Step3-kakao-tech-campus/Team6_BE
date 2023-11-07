package com.example.tripKo.domain.member.api;

import com.example.tripKo._core.security.data.JwtUserDetails;
import com.example.tripKo._core.utils.ApiUtils;
import com.example.tripKo.domain.member.application.WishlistService;
import com.example.tripKo.domain.member.dto.response.RestaurantReservationResponse;
import com.example.tripKo.domain.member.dto.response.WishlistResponse;
import com.example.tripKo.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class WishlistController {
    private final WishlistService wishlistService;
    @PostMapping("/wishlist/{placeId}")
    public ResponseEntity<?> setWishlist(@AuthenticationPrincipal JwtUserDetails jwtUserDetails, @PathVariable Long placeId) {
        Member member = jwtUserDetails.getMember();
        WishlistResponse response = wishlistService.setWishlist(member, placeId);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
        return ResponseEntity.ok(apiResult);
    }

}
