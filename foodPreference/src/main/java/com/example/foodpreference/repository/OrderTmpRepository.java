package com.example.foodpreference.repository;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.Member;
import com.example.foodpreference.domain.OrderTmp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderTmpRepository extends JpaRepository<OrderTmp,Long> {
  Optional<OrderTmp> findByItemAndMember(Item item, Member member);
}
