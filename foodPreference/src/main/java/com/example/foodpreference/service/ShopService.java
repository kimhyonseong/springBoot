package com.example.foodpreference.service;

import com.example.foodpreference.domain.Cart;
import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.Member;
import com.example.foodpreference.domain.OrderTmp;
import com.example.foodpreference.dto.CartDto;
import com.example.foodpreference.dto.CartItem;
import com.example.foodpreference.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
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
  private final OrderTmpRepository orderTmpRepository;
  private final OrderHistoryRepository orderHistoryRepository;
  private final OrderItemRepository orderItemRepository;

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
  public int addCart(CartDto cartDto, User user) {
    try {
      Member member = memberRepository.findById(user.getUsername()).orElseThrow(()->new UsernameNotFoundException("no login"));
      Item item = itemRepository.findByIdx(cartDto.getItemIdx()).orElseThrow(NullPointerException::new);

      // 재고 확인
      if (cartDto.getAmount() > item.getQuantity()) {
        throw new IllegalStateException("amount error");
      }
      Cart cart = cartRepository.findByMemberAndItem(member, item).orElse(new Cart());
      cart.setAmount(cartDto.getAmount());
      cart.setItem(item);
      cart.setMember(member);

      cartRepository.save(cart);

      return 200;
    } catch (IllegalStateException e) {
      log.error("addCart error : amount error");
      return 501;
    } catch (UsernameNotFoundException e) {
      log.error("addCart error : no login");
      return 402;
    } catch (NullPointerException e) {
      log.error("addCart error : item is null or no login");
      return 401;
    } catch (RuntimeException e) {
      log.error("addCart error");
      return 400;
    }
  }

  @Transactional
  public int updateCart(Long idx, int amount) {
    try{
      Cart cart = cartRepository.findById(idx).orElseThrow(()->new IllegalArgumentException("no cart"));

      // 재고 확인
      if (amount > cart.getItem().getQuantity()) {
        throw new IllegalStateException("amount error");
      }
      cart.setAmount(amount);

      cartRepository.save(cart);

      return 200;
    } catch (IllegalStateException e) {
      log.error("update cart error : amount error");
      return 501;
    } catch (IllegalArgumentException e) {
      log.error("update cart error : no cart");
      return 401;
    } catch (RuntimeException e) {
      log.error("update cart error");
      return 400;
    }
  }

  @Transactional
  public int deleteCart(Long idx) {
    try {
      Cart cart = cartRepository.findById(idx).orElseThrow(()->new IllegalArgumentException("no cart"));
      cartRepository.delete(cart);
      return 200;
    } catch (IllegalArgumentException e) {
      log.error("delete cart : no cart");
      return 401;
    } catch (RuntimeException e) {
      log.error("delete cart");
      return 400;
    }
  }

  @Transactional
  public int deleteAllCart(User user) {
    try {
      Member member = memberRepository.findById(user.getUsername()).orElseThrow();
      cartRepository.deleteCartByMember(member.getIdx());
      return 200;
    } catch (IllegalArgumentException e) {
      log.error("delete cart : no cart");
      return 401;
    } catch (RuntimeException e) {
      log.error("delete cart - "+e.getMessage());
      return 400;
    }
  }

  @Transactional
  public int deleteCartList(List<Long> itemIdxList) {
    try {
      cartRepository.deleteCartByList(itemIdxList);
      return 200;
    } catch (RuntimeException e) {
      log.error("delete cart list error - "+e.getMessage());
    }
    return 400;
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

  @Transactional
  public void tmpBuy(Long itemIdx,int amount, User user) {
    try {
      Item item = itemRepository.findByIdx(itemIdx).orElseThrow();
      if (item.getQuantity() < 1) {
        throw new IllegalStateException("sold out");
      } else {
        if (item.getQuantity() - amount < 0) {
          throw new IllegalStateException("");
        }
        item.setQuantity(item.getQuantity() - amount);
      }
      itemRepository.save(item);

      Member member = memberRepository.findById(user.getUsername()).orElseThrow();

      OrderTmp orderTmp = orderTmpRepository.findByItemAndMember(item,member).orElse(new OrderTmp());
      orderTmp.setItem(item);
      orderTmp.setMember(member);
      orderTmp.setAmount(amount);
      orderTmpRepository.save(orderTmp);
    } catch (RuntimeException e) {
      log.error("tmpBuy error");
    }
  }

  @Transactional
  public void buyItem(Long itemIdx, User user) {
    Member member = memberRepository.findById(user.getUsername()).orElseThrow();
    Item item = itemRepository.findByIdx(itemIdx).orElseThrow();
    OrderTmp orderTmp = orderTmpRepository.findByItemAndMember(item, member).orElseThrow();

    orderHistoryRepository.save();
    orderItemRepository.save();
  }
}
