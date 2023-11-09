package com.example.tripKo.domain.file.entity;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.*;

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
@Table(name = "file")
public class File extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(nullable = false, updatable = false)
  private Long id;

  @Column(nullable = false)
  private String type;

  @Column(nullable = false)
  private String name;

  @Builder
  public File(String type, String name) {
    this.type = type;
    this.name = name;
  }

}
