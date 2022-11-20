package com.example.foodpreference.repository;

import com.example.foodpreference.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {
  Item findByIdx(Long idx);
  List<Item> findAll();
  List<Item> findAllByCode(String code);

  @Override
  Page<Item> findAll(Pageable pageable);
  Page<Item> findAllByCode(String code, Pageable pageable);
}
