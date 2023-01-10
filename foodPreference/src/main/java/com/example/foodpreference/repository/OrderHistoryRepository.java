package com.example.foodpreference.repository;

import com.example.foodpreference.domain.OrderHistory;
import com.example.foodpreference.domain.OrderItem;
import com.example.foodpreference.dto.AboutOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory,Long> {
  Optional<OrderHistory> findByIdx(Long idx);

  @Query(value = "SELECT h AS orderHistory, o AS orderItem, i AS item, img AS itemImg "+
          "FROM OrderHistory h " +
          "JOIN FETCH OrderItem o ON h.idx = o.orderHistory.idx " +
          "JOIN FETCH h.member " +
          "JOIN FETCH Item i ON i.idx = o.item.idx " +
          "JOIN FETCH ItemImg img ON i.idx = img.item.idx " +
          "WHERE h.member.idx = :member")
  Slice<AboutOrder> showOrderHistory(@Param("member") Long memberIdz, Pageable pageable);
}
