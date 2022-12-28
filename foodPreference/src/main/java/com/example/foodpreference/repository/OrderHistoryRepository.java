package com.example.foodpreference.repository;

import com.example.foodpreference.domain.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory,Long> {
}
