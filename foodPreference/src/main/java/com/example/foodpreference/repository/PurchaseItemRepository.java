package com.example.foodpreference.repository;

import com.example.foodpreference.domain.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PurchaseItemRepository extends JpaRepository<OrderItem,Long> {
  Page<OrderItem> findAllByRegDate(LocalDateTime localDateTime, Pageable pageable);
  Page<OrderItem> findAll(Pageable pageable);
  Optional<OrderItem> findByIdx(Long idx);
}
