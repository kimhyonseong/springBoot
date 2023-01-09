package com.example.foodpreference.repository;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.OrderItem;
import com.example.foodpreference.dto.AboutOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
  Optional<OrderItem> findByIdx(Long idx);

  @Query(value = "SELECT o.idx AS idx,item_amount AS itemAmount,item_price AS itemPrice,o.item_idx AS itemIdx," +
          "i.name AS name,img_path AS imgPath,file_name AS fileName " +
          "FROM order_item o " +
          "JOIN order_history h ON o.order_idx = h.idx " +
          "JOIN item i ON o.item_idx = i.idx " +
          "LEFT JOIN item_img img ON i.idx = img.item_idx " +
          "WHERE h.member_idx = :member ", nativeQuery = true)
  Page<AboutOrder> showOrderItemDesc(@Param("member") Long memberIdx, Pageable pageable);

  @Query(value = "SELECT o.idx AS idx,item_amount AS itemAmount,item_price AS itemPrice,o.item_idx AS itemIdx," +
          "i.name AS name,img_path AS imgPath,file_name AS fileName " +
          "FROM order_item o " +
          "JOIN order_history h ON o.order_idx = h.idx " +
          "JOIN item i ON o.item_idx = i.idx " +
          "LEFT JOIN item_img img ON i.idx = img.item_idx " +
          "WHERE h.member_idx = :member ", nativeQuery = true)
  List<AboutOrder> showOrderItemList(@Param("member") Long memberIdx, Pageable pageable);
}
