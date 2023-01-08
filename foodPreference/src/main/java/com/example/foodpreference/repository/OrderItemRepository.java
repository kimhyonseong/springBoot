package com.example.foodpreference.repository;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
  Optional<OrderItem> findByIdx(Long idx);
  Optional<OrderItem> findByItem(Item item);

//  @Query(value = "SELECT o FROM OrderItem AS o JOIN FETCH OrderHistory AS h ON o.orderHistory.idx = h.idx "+
//          "JOIN FETCH Item AS i ON o.item.idx = i.idx " +
//          "WHERE h.member.id = :member order by o.regDate desc")
//  @Query(value = "SELECT o FROM OrderItem AS o "+
//          "JOIN FETCH o.item " +
//          "WHERE o.orderHistory.member.id = :member order by o.regDate desc")
  @Query(value = "SELECT o FROM OrderItem AS o " +
          "JOIN OrderHistory AS h " +
          "ON o.orderHistory.idx = h.idx " +
          "JOIN Item AS i " +
          "ON o.item.idx = i.idx " +
          "WHERE h.member.id = :member " +
          "ORDER BY o.regDate")
//  @Query(value = "SELECT o FROM OrderItem AS o " +
//        "JOIN FETCH o.orderHistory h " +
//        "JOIN FETCH o.item i " +
//        "WHERE h.member.id = :member " +
//        "ORDER BY o.regDate")
  Page<OrderItem> showOrderItemDesc(@Param("member") String memberId,Pageable pageable);
}
