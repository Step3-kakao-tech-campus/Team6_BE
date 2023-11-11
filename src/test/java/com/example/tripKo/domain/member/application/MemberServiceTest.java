package com.example.tripKo.domain.member.application;

import com.example.tripKo.IntegrationTest;
import com.example.tripKo._core.errors.exception.BusinessException;
import com.example.tripKo._core.errors.exception.Exception404;
import com.example.tripKo.domain.member.MemberRoleType;
import com.example.tripKo.domain.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;

public class MemberServiceTest extends IntegrationTest {

  @Nested
  @DisplayName("회원가입 실패 테스트")
  class signUpFailTest {

    private Member member;
    @BeforeEach
    void setUp() {
      member = Member.builder()
          .memberId("tripko123")
          .password("tripko1234!")
          .nickName("트립코")
          .realName("김철수")
          .emailAddress("tripko@tripko.com")
          .nationality("한국")
          .role(MemberRoleType.MEMBER)
          .build();
    }

    @Test
    @DisplayName("동일한 이메일이 존재한다면 회원가입은 실패해야 한다.")
    public void fail_signUp_same_email_exist() throws Exception {
      memberRepository.save(member);
      Member newMember = Member.builder().emailAddress("tripko@tripko.com").build();

      assertThrows(BusinessException.class, ()->memberService.signUp(newMember.getMemberId(), newMember.getPassword()
          , newMember.getNickName(), newMember.getRealName(), newMember.getEmailAddress(), newMember.getNationality()));
    }

    @Test
    @DisplayName("동일한 아이디가 존재한다면 회원가입은 실패해야 한다.")
    public void fail_signUp_same_memberId_exist() throws Exception {
      memberRepository.save(member);
      Member newMember = Member.builder().memberId("tripko123").build();

      assertThrows(BusinessException.class, ()->memberService.signUp(newMember.getMemberId(), newMember.getPassword()
          , newMember.getNickName(), newMember.getRealName(), newMember.getEmailAddress(), newMember.getNationality()));
    }

  }

}
