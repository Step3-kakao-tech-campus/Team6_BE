package com.example.tripKo.domain.member.dao;

import com.example.tripKo.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberId(String memberId);
    boolean existsByEmailAddress(String email);
    boolean existsByMemberId(String memberId);
}
