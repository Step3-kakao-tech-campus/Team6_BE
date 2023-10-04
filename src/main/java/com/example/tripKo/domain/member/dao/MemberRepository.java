package com.example.tripKo.domain.member.dao;

import com.example.tripKo.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
