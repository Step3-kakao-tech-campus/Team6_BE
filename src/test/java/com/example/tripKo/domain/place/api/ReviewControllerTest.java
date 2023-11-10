package com.example.tripKo.domain.place.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.example.tripKo.IntegrationTest;
import com.example.tripKo.domain.member.MemberRoleType;
import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.place.dto.request.ReviewRequest;
import com.example.tripKo.domain.place.entity.Place;
import com.example.tripKo.domain.place.entity.Review;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

public class ReviewControllerTest extends IntegrationTest {

  @Nested
  @DisplayName("식당 리뷰 테스트")
  class restaurantReviewTest {

    @Test
    @DisplayName("유효한 요청이면 식당 리뷰 보기는 가능해야 한다.")
    public void should_success_get_restaurant_review() throws Exception {
      Place place = placeTestHelper.generate();
      reviewTestHelper.builder().place(place).build();

      mockMvc.perform(get("/restaurant/reviews/{placeId}", place.getId())
              .param("page", "0"))
          .andExpect(status().isOk())
          .andDo(MockMvcResultHandlers.print())
          .andDo(document);
    }

    @Test
    @DisplayName("유효한 요청이면 식당 리뷰 삭제는 가능해야 한다.")
    public void should_success_delete_restaurant_review() throws Exception {
      Place place = placeTestHelper.generate();
      Review review = reviewTestHelper.builder().place(place).build();
      mockMvc.perform(delete("/restaurant/reviews/{reviewId}", review.getId()))
          .andExpect(status().isOk())
          .andDo(MockMvcResultHandlers.print())
          .andDo(document);
    }

  }
}
