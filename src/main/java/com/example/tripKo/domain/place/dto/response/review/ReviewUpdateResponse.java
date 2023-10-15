package com.example.tripKo.domain.place.dto.response.review;

import com.example.tripKo.domain.place.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Builder
@Getter
@AllArgsConstructor(access = PRIVATE)
public class ReviewUpdateResponse {

    private Long id;
    private Long placeId;
    private String visitTime;
    private int rating;
    private String authorImage;
    private String description;
    private String nickName;
    private List<String> reviewImages = new ArrayList<>();

    public ReviewUpdateResponse(Review review) {
        this.id = review.getId();
        this.placeId = review.getPlace().getId();
        this.visitTime = review.getUsageDate();
        this.rating = review.getScore();
//        this.authorImage = review.getMember().getFile().getName(); //멤버 - 파일 연결 후 적용
        this.authorImage = null;
        this.description = review.getDescription();
        this.nickName = review.getMember().getNickName();
        this.reviewImages = review.getReviewHasFiles().stream()
                .map(r -> createFileNameWithPaths(r.getFile().getName()))
                .collect(Collectors.toList());
    }

    private String createFileNameWithPaths(String filename) {
        return "src" + File.separator
                + "main" + File.separator
                + "resources" + File.separator
                + "reviews" + File.separator
                + "images" + File.separator
                + filename;
    }
}
