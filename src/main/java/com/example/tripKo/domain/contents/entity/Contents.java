package com.example.tripKo.domain.contents.entity;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.example.tripKo.domain.Place;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.tripKo.domain.file.entity.ContentsFile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@DynamicInsert
@DynamicUpdate
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "contents")
public class Contents {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(nullable = false, updatable = false)
  private Long id;

  @Column(nullable = false)
  private Long page;

  @Column(nullable = false)
  private String description;

  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "place_id", nullable = false)
  private Place place;

  @OneToMany(mappedBy = "contents")
  private final List<ContentsMenu> contentsMenus = new ArrayList<>();

  @OneToMany(mappedBy = "contents")
  private final List<ContentsFile> contentsFiles = new ArrayList<>();

  @Builder
  private Contents(Long page, String description, Place place) {
    this.page = page;
    this.description = description;
    this.place = place;
  }

}
