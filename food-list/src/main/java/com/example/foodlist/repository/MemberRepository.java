package com.example.foodlist.repository;

import com.example.foodlist.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByMemberId(String memberId);

    List<Member> findAllByMemberId(String memberId);

    Member findByMemberIdAndMemberPw(String memberId, String memberPw);

    List<Member> findAllByStateAndUpdDateBefore(Integer state, LocalDateTime updateDate);
}
