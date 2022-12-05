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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

//@Import({ShopService.class})
@ExtendWith(MockitoExtension.class)
@WithCustomUser(userName = "admin", password = "1234", role = "USER")
class ShopServiceTest {
  @InjectMocks
  private ShopService shopService;
  @Mock
  private MemberRepository memberRepository;
  @Mock
  private ItemRepository itemRepository;
  @Mock
  private CartRepository cartRepository;

  @Nested
  class showCart {
    @Test
    void success() {
      // given
      Optional<Member> member = Optional.of(new Member());
      member.get().setIdx(1L);
      member.get().setId("admin");
      member.get().setPassword("1234");
      member.get().setRole("USER");

      Item item = new Item();
      item.setIdx(1L);
      item.setCode("한식");
      item.setName("김치");
      item.setQuantity(100);
      item.setPrice(1000);

      Cart cart1 = new Cart();
      cart1.setIdx(1L);
      cart1.setMember(member.get());
      cart1.setAmount(5);
      cart1.setItem(item);

      List<Cart> cartList = new ArrayList<>();
      cartList.add(cart1);
      Page<Cart> cartPage = new PageImpl<>(cartList);

      given(memberRepository.findById(anyString())).willReturn(member);
      given(cartRepository.findAllByMember(any(),any())).willReturn(cartPage);

      //테스트용
      given(memberRepository.findById(anyString())).willReturn(member);
      given(cartRepository.findAllByMember(any())).willReturn(cartList);

      assertNotNull(SecurityContextHolder.getContext().getAuthentication());
      System.out.println(SecurityContextHolder.getContext().getAuthentication());

      //when

      // 통과
      List<Cart> showCartTest = shopService.showCart();
      assertEquals(1L,showCartTest.get(0).getIdx());

      User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      Page<Cart> showCart = shopService.showCart(user, Pageable.ofSize(1),0);
//      Page<Cart> showCart = shopService.showCart((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
//              Pageable.ofSize(1),0);

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