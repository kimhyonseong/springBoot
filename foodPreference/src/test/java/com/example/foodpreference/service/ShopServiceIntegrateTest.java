package com.example.foodpreference.service;

import com.example.foodpreference.annotation.WithCustomUser;
import com.example.foodpreference.domain.*;
import com.example.foodpreference.dto.OrderDto;
import com.example.foodpreference.repository.CartRepository;
import com.example.foodpreference.repository.MemberRepository;
import com.example.foodpreference.repository.OrderHistoryRepository;
import com.example.foodpreference.repository.OrderItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Objects;

@SpringBootTest
@WithCustomUser(userName = "admin", password = "1234", role = "USER")
class ShopServiceIntegrateTest {
  @Autowired
  MemberRepository memberRepository;
  @Autowired
  CartRepository cartRepository;
  @Autowired
  OrderHistoryRepository orderHistoryRepository;
  @Autowired
  OrderItemRepository orderItemRepository;

  @Test
  void buyAllItemTest() {
    OrderDto orderDto = new OrderDto();
    orderDto.setMode("all");
    orderDto.setAddressee("이순신");
    orderDto.setMemberAddress("조선");

    try {
      Member member = memberRepository.findById("admin").orElseThrow(()->new UsernameNotFoundException("not Member"));
      List<Cart> cartItems = null;

      if (Objects.equals(orderDto.getMode(), "all")) {
        cartItems = cartRepository.findCartAllByMember(member, Pageable.unpaged());
      } else {
        cartItems = cartRepository.findAllByCartIdxList(orderDto.getCartIdxList());
      }
      System.out.println("카트 아이템 - "+cartItems);

      if (cartItems.size() > 0) {
        // 주문내역 저장
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setAddressee(orderDto.getAddressee());
        orderHistory.setMemberAddress(orderDto.getMemberAddress());
        orderHistory.setMember(member);
        orderHistory.setDeliverCost(2500);
        OrderHistory saveHistory = orderHistoryRepository.save(orderHistory);
        System.out.println("저장된 히스토리 - "+saveHistory);

        for (Cart cartItem : cartItems) {
          // 주문 내역 속 아이템 저장
          OrderItem orderItem = new OrderItem();
          orderItem.setItem(cartItem.getItem());
          orderItem.setItemAmount(orderDto.getAmount());
          orderItem.setItemPrice(orderDto.getAmount() * cartItem.getItem().getPrice());
          orderItem.setOrderHistory(saveHistory);
          OrderItem savedItem = orderItemRepository.save(orderItem);

          OrderItem showItem = orderItemRepository.findById(savedItem.getIdx()).orElseThrow();
          System.out.println("저장된 아이템 - "+showItem.getOrderHistory());
        }

        OrderHistory savedHistory = orderHistoryRepository.findByIdx(1L).orElseThrow();
        System.out.println("저장된 히스토리 - 아이템 리스트 : "+savedHistory);
      }

      System.out.println(200);
    } catch (NullPointerException | UsernameNotFoundException e) {
      System.out.println(401);
    } catch (RuntimeException e) {
      System.out.println(400);
    }
  }
}