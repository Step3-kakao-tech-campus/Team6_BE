package com.example.tripKo.domain.member.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.example.tripKo.IntegrationTest;
import com.example.tripKo.domain.member.dto.request.SignUpRequest;
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
          .nickName("트립코")
          .realName("김철수")
          .email("tripko@tripko.com")
          .nationality("한국")
          .build();

      mockMvc.perform(post("/sign-up")
              .content(objectMapper.writeValueAsString(request))
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isCreated())
          .andDo(MockMvcResultHandlers.print())
          .andDo(document);
    }

  }
}
