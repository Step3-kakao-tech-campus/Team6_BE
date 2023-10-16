package com.example.tripKo.domain.place.entity;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.tripKo.domain.BaseTimeEntity;
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
public class Contents extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(nullable = false, updatable = false)
  private Long id;

  @Column(nullable = false)
  private Long page;

  @Column(nullable = false)
  private String description;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "place_id", nullable = false)
  private Place place;

  @OneToMany(mappedBy = "contents")
  private final List<ContentsMenu> contentsMenus = new ArrayList<>();

  @OneToMany(mappedBy = "contents")
  private final List<ContentsHasFile> contentsHasFiles = new ArrayList<>();

  @Builder
  private Contents(Long page, String description, Place place) {
    this.page = page;
    this.description = description;
    this.place = place;
  }

  //테스트용
  public void addContentsMenus(ContentsMenu menu) {
    contentsMenus.add(menu);
  }

  //테스트용
  public void addContentsFiles(ContentsHasFile contentsHasFile) {
    contentsHasFiles.add(contentsHasFile);
  }

}
