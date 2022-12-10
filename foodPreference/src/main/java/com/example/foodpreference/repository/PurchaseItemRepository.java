package com.example.foodpreference.repository;

import com.example.foodpreference.domain.PurchaseHistory;
import com.example.foodpreference.domain.PurchaseItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem,Long> {
  Page<PurchaseItem> findAllByRegDate(LocalDateTime localDateTime, Pageable pageable);
  Page<PurchaseItem> findAll(Pageable pageable);
  Optional<PurchaseItem> findByIdx(Long idx);
}
