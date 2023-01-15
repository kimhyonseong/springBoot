package com.example.foodpreference.repository;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.Member;
import com.example.foodpreference.domain.OrderHistory;
import com.example.foodpreference.domain.OrderItem;
import com.example.foodpreference.dto.OrderDto;
import com.example.foodpreference.dto.OrderHistoryDto;
import com.example.foodpreference.dto.OrderItemDto;
import com.example.foodpreference.dto.OrderItemImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.*;

@SpringBootTest
class OrderHistoryRepositoryTest {
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
  void showOrderHistoryTest() {
    Member member = memberRepository.findById("admin").orElseThrow();
    Item item1 = itemRepository.findByIdx(1L).orElseThrow();
    Item item2 = itemRepository.findByIdx(2L).orElseThrow();
    Item item3 = itemRepository.findByIdx(3L).orElseThrow();

    OrderHistory newOrderHistory = new OrderHistory();
    newOrderHistory.setMemberAddress("이순신");
    newOrderHistory.setAddressee("한국");
    newOrderHistory.setDeliverCost(2500);
    newOrderHistory.setMember(member);
    newOrderHistory.setOrderState(10);
    OrderHistory saveOrderHistory = orderHistoryRepository.save(newOrderHistory);

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

    OrderHistory newOrderHistory2 = new OrderHistory();
    newOrderHistory2.setMemberAddress("이순신");
    newOrderHistory2.setAddressee("한국");
    newOrderHistory2.setDeliverCost(2500);
    newOrderHistory2.setMember(member);
    newOrderHistory2.setOrderState(10);
    OrderHistory saveOrderHistory2 = orderHistoryRepository.save(newOrderHistory2);

    OrderItem orderItem4 = new OrderItem();
    orderItem4.setItem(item1);
    orderItem4.setOrderHistory(saveOrderHistory2);
    orderItem4.setItemPrice(5000);
    orderItem4.setItemAmount(5);
    orderItemRepository.save(orderItem4);

    OrderItem orderItem5 = new OrderItem();
    orderItem5.setItem(item2);
    orderItem5.setOrderHistory(saveOrderHistory2);
    orderItem5.setItemPrice(500);
    orderItem5.setItemAmount(10);
    orderItemRepository.save(orderItem5);

    OrderItem orderItem6 = new OrderItem();
    orderItem6.setItem(item3);
    orderItem6.setOrderHistory(saveOrderHistory2);
    orderItem6.setItemPrice(1000);
    orderItem6.setItemAmount(7);
    orderItemRepository.save(orderItem6);

    System.out.println("시작");

    Slice<OrderHistoryDto> orderHistory = orderHistoryRepository.showOrderHistory(2L, Pageable.unpaged());

    /*
    {
        order : [
          {
            주소 : "주소",
            받는 사람: "이름",
            orderItem : [
              {
                수량 : 10,
                총 가격 : 10000,
                이름 : "사과",
                이미지 : "http://img.co.kr/image1.jpg",
                아이템 링크(아이템 스퀀스) : 1L
              },
              {
                수량 : 20,
                총 가격 : 20000,
                이름 : "바나나",
                이미지 : "http://img.co.kr/image2.jpg",
                아이템 링크(아이템 스퀀스) : 2L
              }
            ]
          }, {}, {}
        ]
    }
     */
    Map<String,Object> order = new HashMap<>();
    List<Object> orderList = new ArrayList<>();
    List<Object> dtoList = new ArrayList<>();

    for (int i=0; i<orderHistory.getContent().size(); i++) {
      OrderDto orderDto = new OrderDto();
      orderDto.setOrderState(orderHistory.getContent().get(i).getOrder_state());
      orderDto.setAddressee(orderHistory.getContent().get(i).getAddressee());
      orderDto.setMemberAddress(orderHistory.getContent().get(i).getMember_address());
      orderDto.setDeliverCost(orderHistory.getContent().get(i).getDeliver_cost());

      List<OrderItemImpl> orderItemDtoList = orderItemRepository.orderItemList(orderHistory.getContent().get(i).getIdx());

      for (OrderItemImpl orderItem : orderItemDtoList) {
        OrderItemDto orderItemDto = new OrderItemDto();

        orderItemDto.setIdx(orderItem.getIdx());
        orderItemDto.setItemIdx(orderItem.getItemIdx());
        orderItemDto.setName(orderItem.getName());
        orderItemDto.setPath(orderItem.getPath());
        orderItemDto.setImgName(orderItem.getImgName());
        orderItemDto.setAmount(orderItem.getAmount());
        orderItemDto.setPrice(orderItem.getPrice());

        System.out.println(orderItem.getName());

        dtoList.add(orderItemDto);
      }
      orderDto.setList(dtoList);

      orderList.add(orderDto);
    }

    order.put("order",orderList);
    System.out.println(order);
  }
}