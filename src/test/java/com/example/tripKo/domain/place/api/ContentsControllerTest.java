package com.example.tripKo.domain.place.api;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.tripKo.IntegrationTest;
import com.example.tripKo.domain.file.entity.File;
import com.example.tripKo.domain.place.Category;
import com.example.tripKo.domain.place.dto.response.info.FestivalResponse;
import com.example.tripKo.domain.place.dto.response.info.RestaurantResponse;
import com.example.tripKo.domain.place.entity.Contents;
import com.example.tripKo.domain.place.entity.ContentsHasFile;
import com.example.tripKo.domain.place.entity.ContentsMenu;
import com.example.tripKo.domain.place.entity.Place;
import com.example.tripKo.domain.place.entity.PlaceFestival;
import com.example.tripKo.domain.place.entity.PlaceRestaurant;
import com.example.tripKo.domain.place.entity.PlaceTouristSpot;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

public class ContentsControllerTest extends IntegrationTest {

  private Place place;

  @BeforeEach
  void setUp() {
    place = placeTestHelper.generate();
  }

  @Nested
  @DisplayName("플레이스 상세보기 테스트")
  class get_detail_place {

    @Test
    @DisplayName("유효한 요청이면 식당 상세보기는 성공한다.")
    public void should_success_get_restaurant_info() throws Exception {
      PlaceRestaurant placeRestaurant = PlaceRestaurant.builder()
          .contact_info("010-1111-1111")
          .openingTime("10:00")
          .closingTime("23:00")
          .breakStartTime("15:00")
          .breakEndTime("16:00")
          .holiday(1)
          .place(place)
          .build();
      placeRestaurantRepository.save(placeRestaurant);

      Contents contents = contentsTestHelper.builder().place(placeRestaurant.getPlace()).build();
      contentsRepository.save(contents);

      mockMvc.perform(get("/restaurant/{id}", place.getId()))
          .andExpect(status().isOk())
          .andDo(MockMvcResultHandlers.print())
          .andDo(document);
    }

    @Test
    @DisplayName("유효한 요청이면 축제 상세보기는 성공한다.")
    public void should_success_get_festival_info() throws Exception {
      PlaceFestival placeFestival = PlaceFestival.builder()
          .startDate("2023/10/1")
          .endDate("2023/10/5")
          .place(place)
          .reservationAvailable(true)
          .build();
      placeFestivalRepository.save(placeFestival);

      Contents contents = contentsTestHelper.builder().place(placeFestival.getPlace()).build();
      contentsRepository.save(contents);

      mockMvc.perform(get("/festival/{id}", place.getId()))
          .andExpect(status().isOk())
          .andDo(MockMvcResultHandlers.print())
          .andDo(document);
    }

    @Test
    @DisplayName("유효한 요청이면 관광지 상세보기는 성공한다.")
    public void should_success_get_touristSpot_info() throws Exception {
      PlaceTouristSpot placeTouristSpot = PlaceTouristSpot.builder()
          .place(place)
          .build();
      placeTouristSpotRepository.save(placeTouristSpot);

      Contents contents = contentsTestHelper.builder().place(placeTouristSpot.getPlace()).build();
      contentsRepository.save(contents);

      mockMvc.perform(get("/touristSpot/{id}", place.getId()))
          .andExpect(status().isOk())
          .andDo(MockMvcResultHandlers.print())
          .andDo(document);
    }
  }


}
