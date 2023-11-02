package com.example.tripKo.domain.member.entity;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PACKAGE;
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
import lombok.AllArgsConstructor;
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
@AllArgsConstructor(access = PACKAGE)
@Table(name = "member")
public class Member extends BaseTimeEntity {

  public static final String LOGIN_ID_INVALID = "로그인 아이디는 6~20자 영어, 숫자만 가능합니다.";
  public static final String LOGIN_ID_REGEX = "^[a-zA-Z0-9]{6,20}";

  public static final String PASSWORD_INVALID = "비밀번호는 8~16자여야 하고 영어, 숫자, 특수문자가 포함되어야 합니다.";
  public static final String PASSWORD_REGEX = "^(?=.*?[A-Za-z])(?=.*?\\d)(?=.*?[!@#$%^&*(),.-]).{8,16}$";

  public static final String REAL_NAME_INVALID = "이름은 1~30자 한글, 영어만 가능합니다.";
  public static final String REAL_NAME_REGEX = "^[a-zA-Z가-힣]{1,30}";

  public static final String NICK_NAME_INVALID = "닉네임은 6~20자 한글, 영어만 가능합니다.";
  public static final String NICK_NAME_REGEX = "^[a-zA-Z가-힣]{6,20}";

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
      String password, String birthday, String nationality) {
    this.role = role;
    this.realName = realName;
    this.nickName = nickName;
    this.emailAddress = emailAddress;
    this.memberId = memberId;
    this.password = password;
    this.birthday = birthday;
    this.nationality = nationality;
  }

}
