package com.example.tripKo.domain.member.api;

import com.example.tripKo._core.security.data.JwtUserDetails;
import com.example.tripKo._core.utils.ApiUtils;
import com.example.tripKo.domain.member.application.WishlistService;
import com.example.tripKo.domain.member.dto.response.wishlist.WishlistConfirmResponse;
import com.example.tripKo.domain.member.dto.response.wishlist.WishlistResponse;
import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.place.PlaceType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class WishlistController {

  private final WishlistService wishlistService;

  @PostMapping("/wishlist/{placeId}")
  public ResponseEntity<?> setWishlist(@AuthenticationPrincipal JwtUserDetails jwtUserDetails,
      @PathVariable Long placeId) {
    Member member = jwtUserDetails.getMember();
    WishlistConfirmResponse response = wishlistService.setWishlist(member, placeId);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
    return ResponseEntity.ok(apiResult);
  }

  @DeleteMapping("/wishlist/{placeId}")
  public ResponseEntity<?> deleteWishlist(@AuthenticationPrincipal JwtUserDetails jwtUserDetails,
      @PathVariable Long placeId) {
    Member member = jwtUserDetails.getMember();
    WishlistConfirmResponse response = wishlistService.deleteWishlist(member, placeId);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
    return ResponseEntity.ok(apiResult);
  }

  @GetMapping("/userinfo/wishlist/restaurant")
  public ResponseEntity<?> getRestaurantWishlist(@AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
    Member member = jwtUserDetails.getMember();
    List<WishlistResponse> response = wishlistService.getWishlist(member, PlaceType.RESTAURANT);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
    return ResponseEntity.ok(apiResult);
  }

  @GetMapping("/userinfo/wishlist/festival")
  public ResponseEntity<?> getFestivalWishlist(@AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
    Member member = jwtUserDetails.getMember();
    List<WishlistResponse> response = wishlistService.getWishlist(member, PlaceType.FESTIVAL);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
    return ResponseEntity.ok(apiResult);
  }

  @GetMapping("/userinfo/wishlist/touristSpot")
  public ResponseEntity<?> getTouristSpotWishlist(@AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
    Member member = jwtUserDetails.getMember();
    List<WishlistResponse> response = wishlistService.getWishlist(member, PlaceType.TOURIST_SPOT);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
    return ResponseEntity.ok(apiResult);
  }

}
