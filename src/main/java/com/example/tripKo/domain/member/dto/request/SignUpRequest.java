package com.example.tripKo.domain.member.dto.request;

import static com.example.tripKo.domain.member.entity.Member.LOGIN_ID_INVALID;
import static com.example.tripKo.domain.member.entity.Member.LOGIN_ID_REGEX;
import static com.example.tripKo.domain.member.entity.Member.NICK_NAME_INVALID;
import static com.example.tripKo.domain.member.entity.Member.NICK_NAME_REGEX;
import static com.example.tripKo.domain.member.entity.Member.PASSWORD_INVALID;
import static com.example.tripKo.domain.member.entity.Member.PASSWORD_REGEX;
import static com.example.tripKo.domain.member.entity.Member.REAL_NAME_INVALID;
import static com.example.tripKo.domain.member.entity.Member.REAL_NAME_REGEX;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = PACKAGE)
@NoArgsConstructor(access = PRIVATE)
@Builder
public class SignUpRequest {

  @Pattern(regexp = LOGIN_ID_REGEX, message = LOGIN_ID_INVALID)
  private String memberId;
  @Pattern(regexp = PASSWORD_REGEX, message = PASSWORD_INVALID)
  private String password;
  @Pattern(regexp = NICK_NAME_REGEX, message = NICK_NAME_INVALID)
  private String nickName;
  @Pattern(regexp = REAL_NAME_REGEX, message = REAL_NAME_INVALID)
  private String realName;
  @Email
  private String email;

  private String nationality;

}
