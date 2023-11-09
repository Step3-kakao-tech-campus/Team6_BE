package com.example.tripKo.domain.place;

import com.example.tripKo.domain.place.dao.ContentsRepository;
import com.example.tripKo.domain.place.entity.Contents;
import com.example.tripKo.domain.place.entity.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContentsTestHelper {

  @Autowired
  ContentsRepository contentsRepository;
  @Autowired
  PlaceTestHelper placeTestHelper;

  public Contents generate() {
    return this.builder().build();
  }

  public ContentsBuilder builder() {
    return new ContentsBuilder();
  }

  public final class ContentsBuilder {
    private Long page;
    private String description;
    private Place place;

    public ContentsBuilder page(Long page) {
      this.page = page;
      return this;
    }

    public ContentsBuilder description(String description) {
      this.description = description;
      return this;
    }

    public ContentsBuilder place(Place place) {
      this.place = place;
      return this;
    }

    public Contents build() {
      return contentsRepository.save(Contents.builder()
          .page(page != null ? page : 10)
          .description(description != null ? description : "설명")
          .place(place != null ? place : placeTestHelper.generate())
          .build());
    }

  }

}

