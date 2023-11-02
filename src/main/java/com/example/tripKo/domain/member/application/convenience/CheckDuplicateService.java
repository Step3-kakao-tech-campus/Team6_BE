package com.example.tripKo.domain.member.application.convenience;

import com.example.tripKo.domain.member.dao.MemberRepository;
import com.example.tripKo.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CheckDuplicateService {

  private final MemberRepository memberRepository;

  public boolean isDuplicateEmail(String email) {
    return memberRepository.existsByEmailAddress(email);
  }

  public boolean isDuplicateLoginId(String memberId) {
    return memberRepository.existsByMemberId(memberId);
  }

}
