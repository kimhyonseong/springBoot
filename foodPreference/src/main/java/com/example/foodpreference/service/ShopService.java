package com.example.foodpreference.service;

import com.example.foodpreference.domain.Cart;
import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.Member;
import com.example.foodpreference.dto.CartDto;
import com.example.foodpreference.dto.CartItem;
import com.example.foodpreference.repository.CartRepository;
import com.example.foodpreference.repository.ItemRepository;
import com.example.foodpreference.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopService {
  private final CartRepository cartRepository;
  private final ItemRepository itemRepository;
  private final MemberRepository memberRepository;

  public List<CartItem> showCart(User user, Pageable pageable) {
    try {
      Member member = memberRepository.findById(user.getUsername()).orElseThrow(()->new IllegalArgumentException("no login"));
      return cartRepository.findAllJoinMember(member.getIdx(),pageable);
    } catch (RuntimeException e) {
      log.error("showCart error");
      return null;
    }
  }

  @Transactional
  public boolean addCart(CartDto cartDto, User user) {
    try {
      Member member = memberRepository.findById(user.getUsername()).orElseThrow(()->new UsernameNotFoundException("no login"));
      Item item = itemRepository.findByIdx(cartDto.getItemIdx()).orElseThrow(NullPointerException::new);

      Cart cart = cartRepository.findByMemberAndItem(member, item).orElse(new Cart());
      cart.setAmount(cartDto.getAmount());
      cart.setItem(item);
      cart.setMember(member);

      cartRepository.save(cart);

      return true;
    } catch (UsernameNotFoundException e) {
      log.error("addCart error : no login");
      return false;
    } catch (NullPointerException e) {
      log.error("addCart error : item is null");
      return false;
    } catch (RuntimeException e) {
      log.error("addCart error");
      return false;
    }
  }

  @Transactional
  public boolean updateCart(Long idx, int amount) {
    try{
      Cart cart = cartRepository.findById(idx).orElseThrow(()->new IllegalArgumentException("no cart"));
      cart.setAmount(amount);

      cartRepository.save(cart);

      return true;
    } catch (IllegalArgumentException e) {
      log.error("update cart error : no cart");
      return false;
    } catch (RuntimeException e) {
      log.error("update cart error");
      return false;
    }
  }

  @Transactional
  public boolean deleteCart(Long idx) {
    try {
      Cart cart = cartRepository.findById(idx).orElseThrow(()->new IllegalArgumentException("no cart"));
      cartRepository.delete(cart);
      return true;
    } catch (IllegalArgumentException e) {
      log.error("delete cart : no cart");
      return false;
    } catch (RuntimeException e) {
      log.error("delete cart");
      return false;
    }
  }

  @Transactional
  public boolean deleteAllCart(User user) {
    try {
      Member member = memberRepository.findById(user.getUsername()).orElseThrow();
      cartRepository.deleteCartByMember(member.getIdx());
      return true;
    } catch (IllegalArgumentException e) {
      log.error("delete cart : no cart");
      return false;
    } catch (RuntimeException e) {
      log.error("delete cart - "+e.getMessage());
      return false;
    }
  }

  @Transactional
  public boolean deleteCartList(List<Long> itemIdxList) {
    try {
      cartRepository.deleteCartByList(itemIdxList);
      return true;
    } catch (RuntimeException e) {
      log.error("delete cart list error - "+e.getMessage());
    }
    return false;
  }

  public int countCart(User user) {
    try {
      Member member = memberRepository.findById(user.getUsername()).orElseThrow(()->new UsernameNotFoundException("not user"));
      return cartRepository.countByMember(member);
    } catch (NullPointerException | UsernameNotFoundException e) {
      log.error("cart count error - not user");
    } catch (RuntimeException e) {
      log.error("cart count error");
    }
    return 0;
  }
}
