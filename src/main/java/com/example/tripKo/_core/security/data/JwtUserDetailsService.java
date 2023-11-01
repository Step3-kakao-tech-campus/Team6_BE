package com.example.tripKo._core.security.data;

import com.example.tripKo._core.errors.exception.Exception404;
import com.example.tripKo.domain.member.dao.MemberRepository;
import com.example.tripKo.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        return memberRepository.findByMemberId(memberId)
            .map(this::createUserDetails)
            .orElseThrow(() -> new Exception404("유저를 찾을 수 없습니다. id : " + memberId));
    }

    private UserDetails createUserDetails(Member member) {
           return JwtUserDetails.builder()
                   .member(member)
                   .build();
    }
}
