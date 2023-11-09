package com.example.tripKo.domain.member.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.example.tripKo.IntegrationTest;
import com.example.tripKo.domain.member.MemberReservationStatus;
import com.example.tripKo.domain.member.dto.request.SignInRequest;
import com.example.tripKo.domain.member.dto.request.SignUpRequest;
import com.example.tripKo.domain.member.entity.MemberReservationInfo;
import com.example.tripKo.domain.place.dto.request.RestaurantReservationConfirmRequest;
import com.example.tripKo.domain.place.entity.PlaceRestaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

public class MemberControllerTest extends IntegrationTest {

  @Nested
  @DisplayName("회원가입 테스트")
  class signUpTest {

    @Test
    @DisplayName("유효한 요청이면 회원가입은 성공해야 한다.")
    public void success_signUp_valid_request() throws Exception {
      SignUpRequest request = SignUpRequest.builder()
          .memberId("tripko123")
          .password("tripko1234!")
          .nickName("트립코트립코")
          .realName("김철수")
          .email("tripko@tripko.com")
          .nationality("한국")
          .build();

      mockMvc.perform(post("/register")
              .content(objectMapper.writeValueAsString(request))
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isCreated())
          .andDo(MockMvcResultHandlers.print())
          .andDo(document);
    }

    @Test
    @DisplayName("유효하지 않은 아이디면 테스트는 실패한다.")
    public void fail_signUp_invalidId() throws Exception {
      SignUpRequest request = SignUpRequest.builder()
          .memberId("3")
          .password("tripko1234!")
          .nickName("트립코트립코")
          .realName("김철수")
          .email("tripko@tripko.com")
          .nationality("한국")
          .build();

      mockMvc.perform(post("/register")
              .content(objectMapper.writeValueAsString(request))
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("유효하지 않은 비밀번호라면 테스트는 실패한다.")
    public void fail_signUp_invalidPassword() throws Exception {
      SignUpRequest request = SignUpRequest.builder()
          .memberId("tripko123")
          .password("tripko1234")
          .nickName("트립코트립코")
          .realName("김철수")
          .email("tripko@tripko.com")
          .nationality("한국")
          .build();

      mockMvc.perform(post("/register")
              .content(objectMapper.writeValueAsString(request))
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("유효하지 않은 닉네임이라면 테스트는 실패한다.")
    public void fail_signUp_invalidNickname() throws Exception {
      SignUpRequest request = SignUpRequest.builder()
          .memberId("tripko123")
          .password("tripko1234!")
          .nickName("트립코")
          .realName("김철수")
          .email("tripko@tripko.com")
          .nationality("한국")
          .build();

      mockMvc.perform(post("/register")
              .content(objectMapper.writeValueAsString(request))
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("유효하지 않은 이름 테스트는 실패한다.")
    public void fail_signUp_invalidRealName() throws Exception {
      SignUpRequest request = SignUpRequest.builder()
          .memberId("tripko123")
          .password("tripko1234!")
          .nickName("트립코트립코")
          .realName("김철수1")
          .email("tripko@tripko.com")
          .nationality("한국")
          .build();

      mockMvc.perform(post("/register")
              .content(objectMapper.writeValueAsString(request))
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("유효하지 않은 이메일 양식이라면 테스트는 실패한다.")
    public void fail_signUp_invalidEmail() throws Exception {
      SignUpRequest request = SignUpRequest.builder()
          .memberId("tripko123")
          .password("tripko1234!")
          .nickName("트립코트립코")
          .realName("김철수")
          .email("tripkotripko.com")
          .nationality("한국")
          .build();

      mockMvc.perform(post("/register")
              .content(objectMapper.writeValueAsString(request))
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().is5xxServerError());
    }
  }

  @Nested
  @DisplayName("식당 예약 테스트")
  class restaurantReservationTest {

    @Test
    @DisplayName("유효한 요청이면 식당 예약 날짜 선택 요청은 성공해야 한다.")
    public void should_success_restaurant_booking_calendar() throws Exception {
      PlaceRestaurant placeRestaurant = PlaceRestaurant.builder()
          .contact_info("010-1111-1111")
          .openingTime("10:00")
          .closingTime("23:00")
          .breakStartTime("15:00")
          .breakEndTime("16:00")
          .holiday(1)
          .place(placeTestHelper.generate())
          .build();
      placeRestaurantRepository.save(placeRestaurant);

      mockMvc.perform(get("/restaurant/bookings/calendar/{id}", placeRestaurant.getId()))
          .andExpect(status().isOk())
          .andDo(MockMvcResultHandlers.print())
          .andDo(document);
    }

  }


}
