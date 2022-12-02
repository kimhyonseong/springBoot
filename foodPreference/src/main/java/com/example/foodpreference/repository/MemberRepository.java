package com.example.foodpreference.repository;

import com.example.foodpreference.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByIdx(Long idx);
    Optional<Member> findById(String id);
    Member findByName(String name);
    boolean existsById(String id);
}
