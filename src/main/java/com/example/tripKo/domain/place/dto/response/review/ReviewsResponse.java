package com.example.tripKo.domain.place.dto.response.review;

import com.example.tripKo.domain.place.PlaceType;
import com.example.tripKo.domain.place.entity.Place;
import com.example.tripKo.domain.place.entity.Review;
import lombok.Builder;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReviewsResponse {
    @Builder
    @Getter
    static class ReviewDTO {
        private Long reviewId;
        private Long placeId;
        private PlaceType type;
        private String visitTime;
        private int rating;
        private String description;
        private String nickName;
        private String authorImage;
        private List<String> reviewImage = new ArrayList<>();
    }

    private double averageRating;
    private List<ReviewDTO> reviews = new ArrayList<>();

    public ReviewsResponse(List<Review> reviewEntities, Place place) {
        this.averageRating = place.getAverageRating();

        reviews = reviewEntities.stream()
                .map(this::mapReview)
                .collect(Collectors.toList());
    }

    private ReviewDTO mapReview(Review review) {
        ReviewDTO reviewDTO = ReviewDTO.builder()
                .reviewId(review.getId())
                .placeId(review.getPlace().getId())
                .type(review.getPlace().getPlaceType())
                .visitTime(review.getUsageDate())
                .rating(review.getScore())
                .description(review.getDescription())
                .nickName(review.getMember().getNickName())
                .build();
        if (review.getMember().getFile() != null) {
            reviewDTO.authorImage = review.getMember().getFile().getUrl();
        }
        if(review.getReviewHasFiles() != null) {
            try {
                reviewDTO.reviewImage = review.getReviewHasFiles().stream()
                                .map(r -> r.getFile().getUrl())
                                .collect(Collectors.toList());
            }catch (Exception e) {

            }
        }
        return reviewDTO;
    }

//    private String createFileNameWithPaths(String filename) {
//        return "src" + File.separator
//                + "main" + File.separator
//                + "resources" + File.separator
//                + "reviews" + File.separator
//                + "images" + File.separator
//                + filename;
//    }

}
