package com.example.tripKo.domain.place.entity;

import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.place.PlaceType;
import com.example.tripKo.domain.place.dto.request.ReviewUpdateRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.REMOVE;
import static lombok.AccessLevel.PROTECTED;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.EnumType.STRING;

@DynamicInsert
@DynamicUpdate
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "review")
public class Review {

  @GeneratedValue(strategy = IDENTITY)
  @Column(nullable = false, updatable = false)
  @Id
  private Long id;

  @Column(nullable = false)
  private int score;//float가 아니라 int가 되야한다.

  private String description;

  @Column(nullable = false)
  private String usageDate;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "place_id", nullable = false)
  private Place place;
  
//
//    @Column(nullable = false)
//    private boolean isAvailable;

  @Enumerated(value = STRING)
  @Column(nullable = false)
  private PlaceType type;

  @OneToMany(mappedBy = "review", cascade = REMOVE)
  private List<ReviewHasFile> reviewHasFiles = new ArrayList<>();

  @Builder
  public Review(PlaceType placeType, Place place, Member member, String description, int score, String usageDate) {
    this.type = placeType;
    this.place = place;
    this.member = member;
    this.description = description;
    this.score = score;
    this.usageDate = usageDate;
  }

  public void update(ReviewUpdateRequest reviewUpdateRequest) {
    this.score = reviewUpdateRequest.getRating();
    this.description = reviewUpdateRequest.getDescription();
  }
}
