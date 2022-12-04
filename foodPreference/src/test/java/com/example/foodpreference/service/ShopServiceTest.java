package com.example.foodpreference.service;

import com.example.foodpreference.annotation.WithCustomUser;
import com.example.foodpreference.domain.Cart;
import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.Member;
import com.example.foodpreference.repository.CartRepository;
import com.example.foodpreference.repository.ItemRepository;
import com.example.foodpreference.repository.MemberRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@Import({ShopService.class})
@ExtendWith(MockitoExtension.class)
@WithCustomUser
class ShopServiceTest {
  @InjectMocks
  ShopService shopService;
  @Mock
  MemberRepository memberRepository;
  @Mock
  ItemRepository itemRepository;
  @Mock
  CartRepository cartRepository;

  @Nested
  class showCart {
    @Test
    void success() {
      Optional<Member> member = Optional.of(new Member());
      member.get().setIdx(1L);
      member.get().setId("user1");
      member.get().setRole("USER");

      Item item = new Item();
      item.setIdx(1L);
      item.setCode("한식");
      item.setName("김치");
      item.setQuantity(100);
      item.setPrice(1000);

      Cart cart1 = new Cart();
      cart1.setMember(member.get());
      cart1.setAmount(5);
      cart1.setItem(item);

      List<Cart> cartList = new ArrayList<>();
      cartList.add(cart1);
      Page<Cart> cartPage = new PageImpl<>(cartList);

      lenient().when(memberRepository.findById(anyString())).thenReturn(member);
      lenient().when(cartRepository.findAllByMember(any(Member.class),any())).thenReturn(cartPage);

      //ShopService shopService = new ShopService(cartRepository,itemRepository,memberRepository);
      shopService.showCart((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
              Pageable.unpaged(),0);
    }

    @Test
    void fail(){

    }
  }

  @Test
  void addCart() {
  }

  @Test
  void updateCart() {
  }

  @Test
  void deleteCart() {
  }
}