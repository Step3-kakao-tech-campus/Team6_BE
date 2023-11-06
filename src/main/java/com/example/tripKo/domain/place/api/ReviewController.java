package com.example.tripKo.domain.place.api;

import com.example.tripKo._core.errors.exception.Exception404;
import com.example.tripKo._core.security.data.JwtUserDetails;
import com.example.tripKo._core.utils.ApiUtils;
import com.example.tripKo.domain.member.dao.MemberRepository;
import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.place.PlaceType;
import com.example.tripKo.domain.place.application.ReviewService;
import com.example.tripKo.domain.place.dto.request.ReviewUpdateRequest;
import com.example.tripKo.domain.place.dto.response.review.ReviewsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.example.tripKo.domain.place.dto.request.ReviewRequest;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final MemberRepository memberRepository;
    @PostMapping(path ="/restaurant/reviews", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createPlaceRestaurantReview(@AuthenticationPrincipal JwtUserDetails jwtUserDetails, @ModelAttribute @Valid ReviewRequest reviewRequest) {
        Member member = jwtUserDetails.getMember();

        reviewService.createPlaceReview(reviewRequest, PlaceType.RESTAURANT, member);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/restaurant/reviews/{placeId}")
    public ResponseEntity<?> getPlaceRestaurantReviews(@PathVariable Long placeId, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        ReviewsResponse response = reviewService.getReviewsByPlaceId(placeId, PlaceType.RESTAURANT, page);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
        return ResponseEntity.ok(apiResult);
    }

    @PatchMapping(path="/restaurant/reviews/{reviewId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updatePlaceRestaurantReviews(@PathVariable Long reviewId, @ModelAttribute @Valid ReviewUpdateRequest reviewUpdateRequest) {
        reviewService.updateReview(reviewId, reviewUpdateRequest);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok(apiResult);
    }

    @DeleteMapping("/restaurant/reviews/{reviewId}")
    public ResponseEntity<?> deletePlaceRestaurantReviews(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok(apiResult);
    }

    /*
    Festival
    */
    @PostMapping(path ="/festival/reviews", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createFestivalReview(@AuthenticationPrincipal JwtUserDetails jwtUserDetails, @ModelAttribute @Valid ReviewRequest reviewRequest) {
        Member member = jwtUserDetails.getMember();

        reviewService.createPlaceReview(reviewRequest, PlaceType.FESTIVAL, member);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/festival/reviews/{placeId}")
    public ResponseEntity<?> getFestivalReviews(@PathVariable Long placeId, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        ReviewsResponse response = reviewService.getReviewsByPlaceId(placeId, PlaceType.FESTIVAL, page);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
        return ResponseEntity.ok(apiResult);
    }

    @PatchMapping(path="/festival/reviews/{reviewId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateFestivalReviews(@PathVariable Long reviewId, @ModelAttribute @Valid ReviewUpdateRequest reviewUpdateRequest) {
        reviewService.updateReview(reviewId, reviewUpdateRequest);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok(apiResult);
    }

    @DeleteMapping("/festival/reviews/{reviewId}")
    public ResponseEntity<?> deleteFestivalReviews(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok(apiResult);

    }

    /*
    TouristSpot
    */
    @PostMapping(path ="/touristSpot/reviews", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createTouristSpotReview(@AuthenticationPrincipal JwtUserDetails jwtUserDetails, @ModelAttribute @Valid ReviewRequest reviewRequest) {
        Member member = jwtUserDetails.getMember();

        reviewService.createPlaceReview(reviewRequest, PlaceType.TOURIST_SPOT, member);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/touristSpot/reviews/{placeId}")
    public ResponseEntity<?> getTouristSpotReviews(@PathVariable Long placeId, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        ReviewsResponse response = reviewService.getReviewsByPlaceId(placeId, PlaceType.TOURIST_SPOT, page);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
        return ResponseEntity.ok(apiResult);
    }

    @PatchMapping(path="/touristSpot/reviews/{reviewId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateTouristSpotReviews(@PathVariable Long reviewId, @ModelAttribute @Valid ReviewUpdateRequest reviewUpdateRequest) {
        reviewService.updateReview(reviewId, reviewUpdateRequest);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok(apiResult);
    }

    @DeleteMapping("/touristSpot/reviews/{reviewId}")
    public ResponseEntity<Void> deleteTouristSpotReviews(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();

    }
}
