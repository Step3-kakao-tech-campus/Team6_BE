package com.example.tripKo.domain.contents.entity;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.example.tripKo.domain.BaseTimeEntity;
import com.example.tripKo.domain.file.entity.File;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
@Table(name = "contents_menu")
public class ContentsMenu extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(nullable = false, updatable = false)
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "contents_id", nullable = false)
  private Contents contents;

  @Column
  private Long price;

  @Column(nullable = false)
  private String name;

  @Column
  private String description;

  @Column
  private String characteristic;

  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "file_id", nullable = false)
  private File file;

  @Builder
  private ContentsMenu(Contents contents, Long price, String name, String description, String characteristic, File file) {
    this.contents = contents;
    this.price = price;
    this.name = name;
    this.description = description;
    this.characteristic = characteristic;
    this.file = file;
  }

}
