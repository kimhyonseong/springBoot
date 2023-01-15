package com.example.foodpreference.repository;

import com.example.foodpreference.domain.OrderHistory;
import com.example.foodpreference.domain.OrderItem;
import com.example.foodpreference.dto.AboutOrder;
import com.example.foodpreference.dto.OrderHistoryDto;
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

//  @Query(value = "SELECT h "+
//          "FROM OrderHistory h " +
//          "JOIN FETCH h.orderItemList o " +
//          "JOIN FETCH h.member " +
//          "JOIN FETCH o.item " +
//          "WHERE h.member.idx = :member")
  @Query(value = "SELECT idx,addressee,member_address,order_state,deliver_cost,reg_date FROM order_history h " +
          "WHERE h.member_idx = :member", nativeQuery = true)
  //Slice<AboutOrder> showOrderHistory(@Param("member") Long memberIdz, Pageable pageable);
  Slice<OrderHistoryDto> showOrderHistory(@Param("member") Long memberIdz, Pageable pageable);
}
