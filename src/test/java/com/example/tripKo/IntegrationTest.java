package com.example.tripKo;

import com.example.tripKo.domain.file.dao.FileRepository;
import com.example.tripKo.domain.member.application.MemberService;
import com.example.tripKo.domain.member.dao.MemberRepository;
import com.example.tripKo.domain.member.dao.MemberReservationInfoRepository;
import com.example.tripKo.domain.place.application.ContentsService;
import com.example.tripKo.domain.place.application.PlaceService;
import com.example.tripKo.domain.place.dao.AddressCategoryRepository;
import com.example.tripKo.domain.place.dao.AddressRepository;
import com.example.tripKo.domain.place.dao.ContentsMenuRepository;
import com.example.tripKo.domain.place.dao.ContentsRepository;
import com.example.tripKo.domain.place.dao.PlaceFestivalRepository;
import com.example.tripKo.domain.place.dao.PlaceRepository;
import com.example.tripKo.domain.place.dao.PlaceRestaurantRepository;
import com.example.tripKo.domain.place.dao.PlaceTouristSpotRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@Transactional
@SpringBootTest
public class IntegrationTest {

  /*** repository ***/
  @SpyBean
  protected ContentsRepository contentsRepository;

  @SpyBean
  protected ContentsMenuRepository contentsMenuRepository;

  @SpyBean
  protected PlaceFestivalRepository placeFestivalRepository;

  @SpyBean
  protected PlaceRestaurantRepository placeRestaurantRepository;

  @SpyBean
  protected PlaceTouristSpotRepository placeTouristSpotRepository;

  @SpyBean
  protected PlaceRepository placeRepository;

  @SpyBean
  protected FileRepository fileRepository;

  @SpyBean
  protected AddressRepository addressRepository;

  @SpyBean
  protected AddressCategoryRepository addressCategoryRepository;

  @SpyBean
  protected MemberRepository memberRepository;

  @SpyBean
  protected MemberReservationInfoRepository memberReservationInfoRepository;


  /*** service ***/
  @Autowired
  protected ContentsService contentsService;

  @Autowired
  protected PlaceService placeService;

  @Autowired
  protected MemberService memberService;


  /*** Spring Bean ***/
  @Autowired
  protected WebApplicationContext webApplicationContext;

  @Autowired
  protected ObjectMapper objectMapper;

  @PersistenceContext
  protected EntityManager em;

  protected MockMvc mockMvc;

  protected RestDocumentationResultHandler document;

  @BeforeEach
  protected void setUpAll(WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation) {
    this.document = MockMvcRestDocumentation.document("{class-name}/{method-name}",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()));

    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .addFilter(new CharacterEncodingFilter("UTF-8", true))
        .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
        .alwaysDo(document)
        .build();
  }

}
