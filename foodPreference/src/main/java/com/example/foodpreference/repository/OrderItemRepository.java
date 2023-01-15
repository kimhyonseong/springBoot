package com.example.foodpreference.repository;

import com.example.foodpreference.domain.OrderItem;
import com.example.foodpreference.dto.AboutOrder;
import com.example.foodpreference.dto.OrderItemImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
          "WHERE h.member_idx = :member order by o.reg_date", nativeQuery = true)
  Slice<AboutOrder> showOrderItemDesc(@Param("member") Long memberIdx, Pageable pageable);

  @Query(value = "SELECT o.idx AS idx,o.item_amount AS amount, o.item_price AS price, " +
          "i.idx AS itemIdx,img.img_path AS path,img.file_name AS imgName, i.name AS name " +
          "FROM order_item o " +
          "JOIN item i ON i.idx = o.item_idx " +
          "LEFT JOIN item_img img ON o.item_idx = img.item_idx " +
          "WHERE order_idx = :historyIdx ", nativeQuery = true)
  List<OrderItemImpl> orderItemList(@Param("historyIdx") Long historyIdx);
}
