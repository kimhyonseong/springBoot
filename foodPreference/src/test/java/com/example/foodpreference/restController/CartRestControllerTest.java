package com.example.foodpreference.restController;

import com.example.foodpreference.annotation.WithCustomUser;
import com.example.foodpreference.domain.Member;
import com.example.foodpreference.repository.CartRepository;
import com.example.foodpreference.repository.ItemRepository;
import com.example.foodpreference.repository.MemberRepository;
import com.example.foodpreference.service.MemberService;
import com.example.foodpreference.service.ShopService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
/*
@WebMvcTest(CartRestControllerTest.class)
@AutoConfigureWebMvc
@Import({ShopService.class, MemberService.class})
// 이거로 하면 에러남
*/

class CartRestControllerTest {
//  @Autowired
//  private MockMvc mockMvc;

//  @MockBean
//  private ShopService shopService;
//  @MockBean
//  private MemberService memberService;

//  @Autowired
//  private WebApplicationContext context;
//  @MockBean
//  private CartRepository cartRepository;
//  @MockBean
//  private ItemRepository itemRepository;
//  @MockBean
//  private MemberRepository memberRepository;
//  @MockBean
//  private PasswordEncoder passwordEncoder;

  @Test
  @WithCustomUser
  void showCart() {
    System.out.println(SecurityContextHolder.getContext().getAuthentication());
    System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
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