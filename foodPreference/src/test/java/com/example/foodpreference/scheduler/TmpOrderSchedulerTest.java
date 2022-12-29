package com.example.foodpreference.scheduler;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.Member;
import com.example.foodpreference.domain.OrderTmp;
import com.example.foodpreference.repository.ItemRepository;
import com.example.foodpreference.repository.MemberRepository;
import com.example.foodpreference.repository.OrderTmpRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TmpOrderSchedulerTest {
  @Autowired
  OrderTmpRepository orderTmpRepository;
  @Autowired
  MemberRepository memberRepository;
  @Autowired
  ItemRepository itemRepository;

  @Test
  void deleteTmpOrder() {
    Member member = memberRepository.findById("admin").orElseThrow();
    Item item = itemRepository.findByIdx(1L).orElseThrow();
    Item item1 = itemRepository.findByIdx(2L).orElseThrow();

    OrderTmp order1 = new OrderTmp();
    order1.setMember(member);
    order1.setItem(item);
    order1.setAmount(5);
    order1.setRegDate(LocalDateTime.now());
    orderTmpRepository.save(order1);

    OrderTmp order2 = new OrderTmp();
    order2.setMember(member);
    order2.setItem(item1);
    order2.setAmount(2);
    order2.setRegDate(LocalDateTime.now());
    orderTmpRepository.save(order2);

    System.out.println("시작");
    // N+1 에러
    List<OrderTmp> list1 = orderTmpRepository.tmpOrderList(LocalDateTime.now().plusMinutes(1));
    System.out.println(list1);
  }
}