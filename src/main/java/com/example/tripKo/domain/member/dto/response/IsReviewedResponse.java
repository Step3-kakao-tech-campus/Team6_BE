package com.example.tripKo.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class IsReviewedResponse {
    boolean isReviewed;
    Long reviewId;

    @Builder
    public IsReviewedResponse(boolean isReviewed, Long reviewId) {
        this.isReviewed = isReviewed;
        this.reviewId = reviewId;
    }
}
