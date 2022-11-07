package com.example.foodpreference.repository;

import com.example.foodpreference.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {
  Item findByIdx(Long idx);
}
