package com.example.foodpreference.service;

import com.example.foodpreference.domain.Cart;
import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.Member;
import com.example.foodpreference.dto.CartDto;
import com.example.foodpreference.repository.CartRepository;
import com.example.foodpreference.repository.ItemRepository;
import com.example.foodpreference.repository.MemberRepository;
import com.example.foodpreference.repository.PurchaseItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopService {
  private final PurchaseItemRepository purchaseItemRepository;
  private final CartRepository cartRepository;
  private final ItemRepository itemRepository;
  private final MemberRepository memberRepository;

  @Transactional
  public boolean addCart(CartDto cartDto, @AuthenticationPrincipal User user) {
    try {
      // 로그인 확인해서 무조건 쿠키가 있음
      Member member = memberRepository.findById(user.getUsername());
      Item item = itemRepository.findByIdx(cartDto.getItemIdx());

      if (item == null)
        throw new IllegalArgumentException("아이템이 존재하지 않습니다.");

      Cart cart = new Cart();
      cart.setAmount(cartDto.getAmount());
      cart.setItem(item);
      cart.setMember(member);

      cartRepository.save(cart);

      return true;
    } catch (RuntimeException e) {
      log.error("addCart error : no item");
      return false;
    }
  }
}
