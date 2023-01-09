package com.example.foodpreference.repository;

import com.example.foodpreference.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderItemRepositoryTest {
  @Autowired
  OrderItemRepository orderItemRepository;
  @Autowired
  OrderHistoryRepository orderHistoryRepository;
  @Autowired
  CartRepository cartRepository;
  @Autowired
  MemberRepository memberRepository;
  @Autowired
  ItemRepository itemRepository;

  @Test
  void showOrderItemDescTest() {
    Member member = memberRepository.findById("admin").orElseThrow();
    Item item1 = itemRepository.findByIdx(1L).orElseThrow();
    Item item2 = itemRepository.findByIdx(2L).orElseThrow();
    Item item3 = itemRepository.findByIdx(3L).orElseThrow();

    OrderHistory orderHistory = new OrderHistory();
    orderHistory.setMemberAddress("이순신");
    orderHistory.setAddressee("한국");
    orderHistory.setDeliverCost(2500);
    orderHistory.setMember(member);
    orderHistory.setOrderState(10);
    OrderHistory saveOrderHistory = orderHistoryRepository.save(orderHistory);

    OrderItem orderItem1 = new OrderItem();
    orderItem1.setItem(item1);
    orderItem1.setOrderHistory(saveOrderHistory);
    orderItem1.setItemPrice(5000);
    orderItem1.setItemAmount(5);
    orderItemRepository.save(orderItem1);

    OrderItem orderItem2 = new OrderItem();
    orderItem2.setItem(item2);
    orderItem2.setOrderHistory(saveOrderHistory);
    orderItem2.setItemPrice(500);
    orderItem2.setItemAmount(10);
    orderItemRepository.save(orderItem2);

    OrderItem orderItem3 = new OrderItem();
    orderItem3.setItem(item3);
    orderItem3.setOrderHistory(saveOrderHistory);
    orderItem3.setItemPrice(1000);
    orderItem3.setItemAmount(7);
    orderItemRepository.save(orderItem3);

    System.out.println("시작");
    Page<OrderItem> orderItem = orderItemRepository.showOrderItemDesc("admin", Pageable.unpaged());
    System.out.println(orderItem);
  }
}