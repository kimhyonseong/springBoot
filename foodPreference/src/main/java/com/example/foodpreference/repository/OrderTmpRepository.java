package com.example.foodpreference.repository;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.Member;
import com.example.foodpreference.domain.OrderTmp;
import org.hibernate.criterion.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderTmpRepository extends JpaRepository<OrderTmp,Long> {
  Optional<OrderTmp> findByItemAndMember(Item item, Member member);

  @Query(value = "DELETE FROM order_tmp WHERE DATE_FORMAT(reg_date,'%Y-%m-%d %H:%i:%s') <= (now() - INTERVAL :minute MINUTE)", nativeQuery = true)
  void deleteTmpOrderByTime(@Param("minute") int minute);
}
