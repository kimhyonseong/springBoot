package com.example.foodpreference.repository;

import com.example.foodpreference.domain.OrderHistory;
import com.example.foodpreference.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory,Long> {
  Optional<OrderHistory> findByIdx(Long idx);
}
