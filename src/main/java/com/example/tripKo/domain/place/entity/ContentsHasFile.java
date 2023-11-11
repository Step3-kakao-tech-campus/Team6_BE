package com.example.tripKo.domain.place.entity;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.example.tripKo.domain.BaseTimeEntity;
import com.example.tripKo.domain.file.entity.File;
import com.example.tripKo.domain.place.entity.Contents;

import javax.persistence.*;

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
@Table(name = "contents_has_file")
public class ContentsHasFile extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(nullable = false, updatable = false)
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "contents_id", nullable = false)
  private Contents contents;

  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "file_id", nullable = false)
  private File file;

  @Builder
  private ContentsHasFile(Contents contents, File file) {
    this.contents = contents;
    this.file = file;
  }

}
