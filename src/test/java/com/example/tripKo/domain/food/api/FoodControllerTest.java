package com.example.tripKo.domain.food.api;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.tripKo.IntegrationTest;
import com.example.tripKo.domain.food.entity.Food;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

public class FoodControllerTest extends IntegrationTest {

//  @Test
//  @DisplayName("유효한 요청일 경우 음식 검색은 성공해야 한다.")
//  public void success_get_foods_info() throws Exception {
//
//    foodTestHelper.generate();
//    foodTestHelper.generate();
//    foodTestHelper.generate();
//
//    mockMvc.perform(get("/foods"))
//        .andExpect(status().isOk())
//        .andDo(MockMvcResultHandlers.print())
//        .andDo(document);
//  }

  @Test
  @DisplayName("유효한 요청일 경우 상세정보 보기는 성공해야 한다.")
  public void success_get_foods_info_details() throws Exception {

    Food food = foodTestHelper.generate();

    mockMvc.perform(get("/foods/{foodId}", food.getId()))
        .andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andDo(document);

  }

}
