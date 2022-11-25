package com.example.foodpreference.repository;

import com.example.foodpreference.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByIdx(Long idx);
    Member findById(String id);
    Member findByName(String name);
    boolean existsById(String id);
}
