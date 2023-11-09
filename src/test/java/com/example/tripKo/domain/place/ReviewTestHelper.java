package com.example.tripKo.domain.place;

import com.example.tripKo.domain.member.MemberTestHelper;
import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.place.dao.ReviewRepository;
import com.example.tripKo.domain.place.entity.Place;
import com.example.tripKo.domain.place.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewTestHelper {

  @Autowired
  ReviewRepository reviewRepository;
  @Autowired
  PlaceTestHelper placeTestHelper;
  @Autowired
  MemberTestHelper memberTestHelper;

  public Review generate() {
    return this.builder().build();
  }

  public ReviewBuilder builder() {
    return new ReviewBuilder();
  }

  public final class ReviewBuilder {

    private int score;
    private String description;
    private String usageDate;
    private Member member;
    private Place place;
    private PlaceType type;

    public ReviewBuilder score(int score) {
      this.score = score;
      return this;
    }

    public ReviewBuilder description(String description) {
      this.description = description;
      return this;
    }

    public ReviewBuilder usageDate(String usageDate) {
      this.usageDate = usageDate;
      return this;
    }

    public ReviewBuilder member(Member member) {
      this.member = member;
      return this;
    }

    public ReviewBuilder place(Place place) {
      this.place = place;
      return this;
    }

    public ReviewBuilder type(PlaceType type) {
      this.type = type;
      return this;
    }

    public Review build() {
      return reviewRepository.save(Review.builder()
          .score(score)
          .place(place != null ? place : placeTestHelper.generate())
          .usageDate(usageDate != null ? usageDate : "23/10/03")
          .member(member != null ? member : memberTestHelper.generate())
          .placeType(type != null ? type : PlaceType.RESTAURANT)
          .build());
    }
  }

}
