package com.example.tripKo.domain.place.application;

import com.example.tripKo.IntegrationTest;
import com.example.tripKo.domain.place.entity.Review;
import com.example.tripKo.domain.place.entity.ReviewHasFile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;

public class ReviewServiceTest extends IntegrationTest {

  @Nested
  @DisplayName("리뷰 삭제 테스트")
  class reviewDeleteTest {

    @Test
    @DisplayName("리뷰 삭제 시 성공적으로 삭제된다.")
    public void success_delete_review() throws Exception {
      Review review = reviewTestHelper.generate();
      reviewService.deleteReview(review.getId());

      em.flush();
      em.clear();

      assertThat(reviewRepository.findById(review.getId())).isEmpty();
    }

    @Test
    @DisplayName("리뷰 삭제 시 리뷰 파일은 성공적으로 삭제된다.")
    public void success_delete_review_file() throws Exception {
      Review review = reviewTestHelper.generate();
      ReviewHasFile reviewHasFile = ReviewHasFile.builder()
          .review(review)
          .file(fileTestHelper.generate())
          .build();

      reviewService.deleteReview(review.getId());

      em.flush();
      em.clear();

      assertThat(fileRepository.findById(reviewHasFile.getFile().getId())).isEmpty();
      assertThat(fileRepository.findById(review.getId()));
  }

  }


}
