package com.example.tripKo.domain.member.dto.response;

import lombok.Getter;

@Getter
public class IsReviewedResponse {
    boolean isReviewed;

    public IsReviewedResponse(boolean isReviewed) {
        this.isReviewed = isReviewed;
    }
}
