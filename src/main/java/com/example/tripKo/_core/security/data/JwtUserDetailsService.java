package com.example.tripKo._core.security.data;

import com.example.tripKo.domain.member.MemberRoleType;
import com.example.tripKo.domain.member.dao.MemberRepository;
import com.example.tripKo.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        return memberRepository.findByMemberId(memberId)
            .map(this::createUserDetails)
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    private UserDetails createUserDetails(Member member) {
           return User.builder()
                .username(member.getMemberId())
                .password(member.getPassword())
                .roles(member.getRole().getKey().toString())
                .build();
    }
}
