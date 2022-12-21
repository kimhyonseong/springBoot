package com.example.foodpreference.service;

import com.example.foodpreference.annotation.WithCustomUser;
import com.example.foodpreference.domain.Cart;
import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.Member;
import com.example.foodpreference.dto.CartDto;
import com.example.foodpreference.repository.CartRepository;
import com.example.foodpreference.repository.ItemRepository;
import com.example.foodpreference.repository.MemberRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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

  private static Member member;
  private static Item item;
  private static Cart cart;
  private static CartDto cartDto;
  private static final List<Cart> cartList = new ArrayList<>();
  private static Page<Cart> cartPage;
  private static User user;

  @BeforeAll
  static void before() {
    member = new Member();
    member.setIdx(1L);
    member.setId("admin");
    member.setName("testAdmin");
    member.setPassword("1234");
    member.setRole("USER");

    item = new Item();
    item.setIdx(1L);
    item.setCode("한식");
    item.setName("김치");
    item.setQuantity(100);
    item.setPrice(1000);

    cart = new Cart();
    cart.setIdx(1L);
    cart.setMember(member);
    cart.setAmount(5);
    cart.setItem(item);

    cartList.add(cart);

    cartDto = new CartDto();

    user = mock(User.class);
  }

  @Nested
  class showCart {
    @Test
    void success() {
      // given
      when(user.getUsername()).thenReturn("admin");

      // 스프링 시큐리티 mock
      /*
      Authentication auth = Mockito.mock(Authentication.class);
      lenient().when(auth.getPrincipal()).thenReturn(member);
      lenient().when(auth.isAuthenticated()).thenReturn(true);

      SecurityContext securityContext = Mockito.mock(SecurityContext.class);
      lenient().when(securityContext.getAuthentication()).thenReturn(auth);
      SecurityContextHolder.setContext(securityContext);
      assertNotNull(SecurityContextHolder.getContext().getAuthentication());
      System.out.println(SecurityContextHolder.getContext().getAuthentication());
      */

      given(memberRepository.findById(anyString())).willReturn(Optional.ofNullable(member));
      given(cartRepository.findAllByMember(any(),any())).willReturn(cartList);

      //when
      List<Cart> showCart = shopService.showCart(user, Pageable.ofSize(1));

      //then
      assertEquals(5,showCart.get(0).getAmount());
      assertEquals(item,showCart.get(0).getItem());
    }

    @Test
    void fail(){
      //given

      // member 값이 null 값일 시 showCart error 출력 및 null 반환 예상
      when(memberRepository.findById(anyString())).thenReturn(null);
      // 이 로직은 사용하기 전에 예외 발생
      lenient().when(cartRepository.findAllByMember(any(),any())).thenReturn(cartList);

      //when
      List<Cart> result = shopService.showCart(user,Pageable.ofSize(1));

      //then
      assertNull(result);
    }
  }

  @Nested
  class addCart {
    @Test
    void success() {
      //given
      when(user.getUsername()).thenReturn("admin");
      when(memberRepository.findById(anyString())).thenReturn(Optional.ofNullable(member));
      when(itemRepository.findByIdx(any()).orElse(null)).thenReturn(item);
      when(cartRepository.findByMemberAndItem(any(),any())).thenReturn(Optional.of(new Cart()));
      when(cartRepository.save(any())).thenReturn(cart);

      //when
      boolean result = shopService.addCart(cartDto,user);

      //then
      assertTrue(result);
    }

    @Test
    void loginFail() {
      //given
      when(user.getUsername()).thenReturn("admin");
      when(memberRepository.findById(anyString())).thenThrow(new UsernameNotFoundException("no login"));

      //when
      boolean result = shopService.addCart(cartDto,user);

      //then
      assertFalse(result);
    }

    @Test
    void itemFail() {
      //given
      when(user.getUsername()).thenReturn("admin");
      when(memberRepository.findById(anyString())).thenReturn(Optional.ofNullable(member));
      when(itemRepository.findByIdx(anyLong())).thenReturn(null);

      //when
      boolean result = shopService.addCart(cartDto,user);

      //then
      assertFalse(result);
    }
  }

  @Nested
  class updateCart {
    @Test
    void success() {
      //given
      when(cartRepository.findById(anyLong())).thenReturn(Optional.ofNullable(cart));

      //when
      boolean result = shopService.updateCart(1L,5);

      //then
      assertTrue(result);
    }

    @Test
    void fail() {
      //given
      when(cartRepository.findById(anyLong())).thenThrow(new IllegalArgumentException("no cart"));

      //when
      boolean result = shopService.updateCart(1L,5);

      //then
      assertFalse(result);
    }
  }

  @Nested
  class deleteCart {
    @Test
    void success() {
      //given
      when(cartRepository.findById(anyLong())).thenReturn(Optional.ofNullable(cart));

      //when
      boolean result = shopService.deleteCart(1L);

      //then
      assertTrue(result);
    }

    @Test
    void fail() {
      //given
      when(cartRepository.findById(anyLong())).thenThrow(new IllegalArgumentException("no cart"));

      //when
      boolean result = shopService.deleteCart(1L);

      //then
      assertFalse(result);
    }
  }
}