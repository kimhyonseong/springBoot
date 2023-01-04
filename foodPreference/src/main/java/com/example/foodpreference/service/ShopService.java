package com.example.foodpreference.service;

import com.example.foodpreference.domain.*;
import com.example.foodpreference.dto.CartDto;
import com.example.foodpreference.dto.CartItem;
import com.example.foodpreference.dto.OrderDto;
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

  // 수량 확보를 위한 임시 주문 생성
  @Transactional
  public void tmpBuy(Long itemIdx,int amount, User user) {
    try {
      Item item = itemRepository.findByIdx(itemIdx).orElseThrow();
      Member member = memberRepository.findById(user.getUsername()).orElseThrow();
      OrderTmp orderTmp = orderTmpRepository.findByItemAndMember(item,member).orElse(new OrderTmp());

      // 재고가 0이거나 초과 검사
      if (item.getQuantity() < 1) {
        throw new IllegalStateException("sold out");
      } else {
        if (item.getQuantity() - amount < 0) {
          throw new IllegalStateException("amount over");
        }
        item.setQuantity(item.getQuantity() + orderTmp.getAmount() - amount);
        itemRepository.save(item);
      }

      orderTmp.setItem(item);
      orderTmp.setMember(member);
      orderTmp.setAmount(amount);
      orderTmpRepository.save(orderTmp);
    } catch (RuntimeException e) {
      log.error("tmpBuy error");
    }
  }

  @Transactional
  public int buyOneItem(OrderDto orderDto, Long itemIdx, User user) {
    try {
      // 회원 및 아이템 확인
      Member member = memberRepository.findById(user.getUsername()).orElseThrow(()->new UsernameNotFoundException("not user"));
      Item item = itemRepository.findByIdx(itemIdx).orElseThrow();

      // 임시 주문 목록 삭제
      OrderTmp orderTmp = orderTmpRepository.findByItemAndMember(item, member).orElseThrow(()->new IllegalArgumentException(""));
      orderTmpRepository.delete(orderTmp);

      // 주문내역 저장
      OrderHistory orderHistory = new OrderHistory();
      orderHistory.setAddressee(orderDto.getAddressee());
      orderHistory.setMemberAddress(orderDto.getMemberAddress());
      orderHistory.setMember(member);
      orderHistory.setDeliverCost(2500);
      OrderHistory saveHistory = orderHistoryRepository.save(orderHistory);

      // 주문 내역 속 아이템 저장
      OrderItem orderItem = new OrderItem();
      orderItem.setItem(item);
      orderItem.setItemAmount(orderDto.getAmount());
      orderItem.setItemPrice(orderDto.getAmount() * item.getPrice());
      orderItem.setOrderHistory(saveHistory);
      orderItemRepository.save(orderItem);
    } catch (IllegalArgumentException e) {
      log.error("buyItem error - not normal path or no have tmp order");
      return 402;
    } catch (NullPointerException | UsernameNotFoundException e) {
      log.error("buyItem error - no member");
      return 401;
    } catch (RuntimeException e) {
      log.error("buyItem error");
      return 400;
    }
    return 200;
  }

  @Transactional
  public int buyAllItem(User user) {
    try {
      Member member = memberRepository.findById(user.getUsername()).orElseThrow(()->new UsernameNotFoundException("not Member"));
      List<CartItem> cartItems = cartRepository.findAllJoinMember(member.getIdx(),Pageable.unpaged());

      if (cartItems.size() > 0) {
        // 주문내역 저장
//        OrderHistory orderHistory = new OrderHistory();
//        orderHistory.setAddressee(orderDto.getAddressee());
//        orderHistory.setMemberAddress(orderDto.getMemberAddress());
//        orderHistory.setMember(member);
//        orderHistory.setDeliverCost(2500);
//        OrderHistory saveHistory = orderHistoryRepository.save(orderHistory);
//
//        for (CartItem cartItem : cartItems) {
//          // 주문 내역 속 아이템 저장
//          OrderItem orderItem = new OrderItem();
//          orderItem.setItem(item);
//          orderItem.setItemAmount(orderDto.getAmount());
//          orderItem.setItemPrice(orderDto.getAmount() * item.getPrice());
//          orderItem.setOrderHistory(saveHistory);
//          orderItemRepository.save(orderItem);
//        }
      }

      return 200;
    } catch (NullPointerException | UsernameNotFoundException e) {
      return 401;
    } catch (RuntimeException e) {
      return 400;
    }
  }
}
