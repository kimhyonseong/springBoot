package com.example.foodpreference.repository;

import com.example.foodpreference.domain.Member;
import com.example.foodpreference.domain.PurchaseHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<PurchaseHistory,Long> {
  Page<PurchaseHistory> findAllByRegDate(LocalDateTime localDateTime, Pageable pageable);
  Page<PurchaseHistory> findAll(Pageable pageable);
  Page<PurchaseHistory> findAllByMember(Member member, Pageable pageable);
  Optional<PurchaseHistory> findByIdx(Long idx);
}
