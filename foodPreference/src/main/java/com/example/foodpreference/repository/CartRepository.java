package com.example.foodpreference.repository;

import com.example.foodpreference.domain.Cart;
import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
  List<Cart> findAllByMember(Member member, Pageable pageable);
  Optional<Cart> findByMemberAndItem(Member member, Item item);

  @Query("SELECT c FROM Cart AS c JOIN FETCH c.member JOIN FETCH c.item WHERE c.member.idx= :memberIdx")
  List<Cart> findAllJoinMember(@Param("memberIdx") Long memberIdx, Pageable pageable);
}
