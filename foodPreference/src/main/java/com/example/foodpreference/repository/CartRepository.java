package com.example.foodpreference.repository;

import com.example.foodpreference.domain.Cart;
import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.Member;
import com.example.foodpreference.dto.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
  @Query("SELECT c AS cart FROM Cart AS c JOIN FETCH c.item WHERE c.member = :member")
  List<Cart> findCartAllByMember(@Param("member") Member member, Pageable pageable);

  Optional<Cart> findByMemberAndItem(Member member, Item item);

  @Query("SELECT c AS cart,img AS itemImg FROM Cart AS c JOIN FETCH c.member JOIN FETCH c.item " +
          "LEFT JOIN FETCH ItemImg AS img ON c.item.idx = img.item.idx " +
          "WHERE c.member.idx= :memberIdx")
  List<CartItem> findAllJoinMember(@Param("memberIdx") Long memberIdx, Pageable pageable);

  int countByMember(Member member);

  @Modifying
  @Query("DELETE FROM Cart AS c WHERE c.member.idx = :member")
  void deleteCartByMember(@Param("member") Long member);

  @Modifying
  @Query("DELETE FROM Cart AS c where c.item.idx in (:list)")
  void deleteCartByList(@Param("list") List<Long> list);

  @Query("SELECT c AS cart FROM Cart AS c JOIN FETCH c.item WHERE c.idx IN (:cartIdx)")
  List<Cart> findAllByCartIdxList(@Param("cartIdx") List<Long> cartIdx);
}
