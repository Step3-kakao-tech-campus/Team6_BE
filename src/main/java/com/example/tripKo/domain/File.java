package com.example.tripKo.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class File {
  // 임시용 File 엔티티 (오류때문에 만들어둠)
  // 나중에 File 엔티티 만들어지면 지우면 됨.

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(nullable = false)
  private Long id;

}
