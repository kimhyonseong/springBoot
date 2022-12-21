package com.example.foodpreference.repository;

import com.example.foodpreference.domain.Cart;
import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartRepositoryTest {
  @Autowired
  private ItemRepository itemRepository;
  @Autowired
  private CartRepository cartRepository;
  @Autowired
  private MemberRepository memberRepository;

  @Test
  void showCart() {
    Item item = itemRepository.findByIdx(1L).orElseThrow();
    Member member = memberRepository.findById("admin").orElseThrow();

    Cart cart = new Cart();
    cart.setItem(item);
    cart.setAmount(10);
    cart.setMember(member);
    cartRepository.save(cart);

    Cart cart2 = new Cart();
    cart2.setItem(item);
    cart2.setAmount(10);
    cart2.setMember(member);
    cartRepository.save(cart2);

    System.out.println("findAllJoinMember start");
    // 무조건 2번 실행됨... - 멤버가 한번 더 나옴
    List<Cart> saveCart = cartRepository.findAllJoinMember(member.getIdx(), Pageable.unpaged());
    System.out.println("findAllJoinMember end");
    System.out.println(saveCart);
  }
}