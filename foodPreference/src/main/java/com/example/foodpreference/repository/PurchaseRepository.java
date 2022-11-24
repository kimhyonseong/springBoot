package com.example.foodpreference.repository;

import com.example.foodpreference.domain.Member;
import com.example.foodpreference.domain.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase,Long> {
  Page<Purchase> findAllByRegDate(LocalDateTime localDateTime, Pageable pageable);
  Page<Purchase> findAll(Pageable pageable);
  Page<Purchase> findAllByMember(Member member, Pageable pageable);
  Optional<Purchase> findByIdx(Long idx);
}
