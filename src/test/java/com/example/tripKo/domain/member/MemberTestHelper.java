package com.example.tripKo.domain.member;

import com.example.tripKo.domain.member.MemberRoleType;
import com.example.tripKo.domain.member.dao.MemberRepository;
import com.example.tripKo.domain.member.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberTestHelper {

  @Autowired
  MemberRepository memberRepository;

  public Member generate() {
    return this.builder().build();
  }

  public MemberBuilder builder() {
    return new MemberBuilder();
  }

  public final class MemberBuilder {

    private MemberRoleType role;
    private String realName;
    private String nickName;
    private String emailAddress;
    private String memberId;
    private String password;
    private String nationality;

    public MemberBuilder role(MemberRoleType memberRoleType) {
      this.role = role;
      return this;
    }

    public MemberBuilder realName(String realName) {
      this.realName = realName;
      return this;
    }

    public MemberBuilder nickName(String nickName) {
      this.nickName = nickName;
      return this;
    }

    public MemberBuilder emailAddress(String emailAddress) {
      this.emailAddress = emailAddress;
      return this;
    }

    public MemberBuilder memberId(String memberId) {
      this.memberId = memberId;
      return this;
    }

    public MemberBuilder password(String password) {
      this.password = password;
      return this;
    }

    public MemberBuilder nationality(String nationality) {
      this.nationality = nationality;
      return this;
    }

    public Member build() {
      return memberRepository.save(Member.builder()
          .role(role != null ? role : MemberRoleType.MEMBER)
          .realName(realName != null ? realName : "실제이름")
          .nickName(nickName != null ? nickName : "닉네임")
          .emailAddress(emailAddress != null ? emailAddress : "123@aa.cc")
          .memberId(memberId != null ? memberId : "adminId")
          .password(password != null ? password : "adminPw12!")
          .nationality(nationality != null ? nationality : "한국")
          .build());
    }
  }

}

