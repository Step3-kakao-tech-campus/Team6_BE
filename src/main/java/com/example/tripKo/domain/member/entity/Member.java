package com.example.tripKo.domain.member.entity;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.example.tripKo.domain.BaseTimeEntity;
import com.example.tripKo.domain.file.entity.File;
import com.example.tripKo.domain.member.MemberRoleType;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
@Table(name = "member")
public class Member extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(nullable = false, updatable = false)
  private Long id;

  @Enumerated(value = EnumType.STRING)
  @Column(nullable = false)
  private MemberRoleType role;

  @Column(nullable = false)
  private String realName;

  @Column(nullable = false)
  private String nickName;

  @Column(nullable = false)
  private String emailAddress;

  @Column(nullable = false)
  private String memberId;

  @Column(nullable = false)
  private String password;

  @Column
  private String nationality;

  @Column(nullable = false)
  private String birthday;

  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "file_id")
  private File file;

  @OneToMany(mappedBy = "member")
  private final List<MemberReservationInfo> memberReservationInfos = new ArrayList<>();

  @Builder
  public Member(MemberRoleType role, String realName, String nickName, String emailAddress, String memberId,
      String password, String birthday) {
    this.role = role;
    this.realName = realName;
    this.nickName = nickName;
    this.emailAddress = emailAddress;
    this.memberId = memberId;
    this.password = password;
    this.birthday = birthday;
  }

}
