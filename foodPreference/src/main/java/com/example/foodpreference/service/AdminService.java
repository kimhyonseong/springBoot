package com.example.foodpreference.service;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.Member;
import com.example.foodpreference.repository.ItemRepository;
import com.example.foodpreference.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {
  private final MemberRepository memberRepository;
  private final ItemRepository itemRepository;

  public List<Item> findAdminItem(@AuthenticationPrincipal User user) {
    List<Item> itemList = null;
    Member member = null;

    try {
      member = memberRepository.findById(user.getUsername()).orElseThrow(() ->new UsernameNotFoundException("no member"));
      itemList = itemRepository.findByMember(member.getIdx());
    } catch (UsernameNotFoundException e) {
      log.error("findAdminItem error : no member");
      return null;
    } catch (Exception e) {
      log.error("findAdminItem Runtime exception");
      return null;
    }
    return itemList;
  }
}
