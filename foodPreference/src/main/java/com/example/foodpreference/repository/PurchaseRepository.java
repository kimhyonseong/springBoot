package com.example.foodpreference.repository;

import com.example.foodpreference.domain.Member;
import com.example.foodpreference.domain.OrderHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<OrderHistory,Long> {
  Page<OrderHistory> findAllByRegDate(LocalDateTime localDateTime, Pageable pageable);
  Page<OrderHistory> findAll(Pageable pageable);
  Page<OrderHistory> findAllByMember(Member member, Pageable pageable);
  Optional<OrderHistory> findByIdx(Long idx);
}
