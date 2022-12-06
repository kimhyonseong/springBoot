package com.example.foodpreference.service;

import com.example.foodpreference.annotation.WithCustomUser;
import com.example.foodpreference.domain.Cart;
import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.Member;
import com.example.foodpreference.repository.CartRepository;
import com.example.foodpreference.repository.ItemRepository;
import com.example.foodpreference.repository.MemberRepository;
import org.aspectj.lang.annotation.Before;
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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
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
      member.get().setName("testAdmin");
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

      User user = mock(User.class);
      when(user.getUsername()).thenReturn("admin");

      // 스프링 시큐리티 mock
      Authentication auth = Mockito.mock(Authentication.class);
      lenient().when(auth.getPrincipal()).thenReturn(member);
      lenient().when(auth.isAuthenticated()).thenReturn(true);

      SecurityContext securityContext = Mockito.mock(SecurityContext.class);
      lenient().when(securityContext.getAuthentication()).thenReturn(auth);
      SecurityContextHolder.setContext(securityContext);

      given(memberRepository.findById(anyString())).willReturn(member);
      given(cartRepository.findAllByMember(any(),any())).willReturn(cartPage);

      assertNotNull(SecurityContextHolder.getContext().getAuthentication());
      System.out.println(SecurityContextHolder.getContext().getAuthentication());

      //when
      Page<Cart> showCart = shopService.showCart(user, Pageable.ofSize(1),0);
      assertEquals(5,showCart.getContent().get(0).getAmount());
      assertEquals(item,showCart.getContent().get(0).getItem());
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