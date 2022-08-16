package com.example.foodlist.repository;

import com.example.foodlist.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> {
    List<Member> findByMemberId(String memberId);
}
