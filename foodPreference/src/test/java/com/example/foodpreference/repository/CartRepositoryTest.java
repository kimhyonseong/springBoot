package com.example.foodpreference.repository;

import com.example.foodpreference.domain.Cart;
import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.Member;
import com.example.foodpreference.dto.CartItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    List<Cart> cartList = cartRepository.findAllByMember(member, Pageable.unpaged());
    System.out.println(cartList);
    System.out.println(member);

    List<CartItem> saveCart = cartRepository.findAllJoinMember(member.getIdx(), Pageable.unpaged());
    System.out.println(saveCart.get(0).getItem());
    System.out.println(saveCart.get(0).getItemImg());
    System.out.println(saveCart.get(0).getCart());
    System.out.println(saveCart.get(0));
  }

  @Test
  void count() {
    Member member = memberRepository.findById("admin").orElseThrow();
    System.out.println(cartRepository.countByMember(member));
  }

  @Test
  @Transactional
  void deleteAllByIdx() {
    Member member = memberRepository.findById("admin").orElseThrow();
    cartRepository.deleteCartByMember(member.getIdx());
    System.out.println(cartRepository.findAllByMember(member, Pageable.unpaged()));
  }

  @Test
  @Transactional
  void deleteCartList() {
    List<Long> list = new ArrayList<>();
    list.add(1L);
    list.add(2L);
    System.out.println(cartRepository.findAll());
    cartRepository.deleteCartByList(list);
    System.out.println(cartRepository.findAll());
  }
}