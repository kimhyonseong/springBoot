package com.example.foodpreference.service;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.ItemImg;
import com.example.foodpreference.domain.Member;
import com.example.foodpreference.dto.ItemDto;
import com.example.foodpreference.repository.ItemImgRepository;
import com.example.foodpreference.repository.ItemRepository;
import com.example.foodpreference.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
  private final ItemRepository itemRepository;
  private final ItemImgRepository itemImgRepository;
  private final MemberRepository memberRepository;

  public Map<String,Object> findItem(Long idx) throws RuntimeException{
    Map<String, Object> map = new HashMap<>();

    try {
      Item item = itemRepository.findByIdx(idx);
      map.put("item",item);
      map.put("itemIdx",item.getIdx());
      map.put("name",item.getName());
      map.put("code",item.getCode());
      map.put("description",item.getDescription());
      map.put("price",item.getPrice());
      map.put("quantity",item.getQuantity());
      map.put("state",item.getState());
    } catch (RuntimeException e) {
      log.error("존재하지 않는 idx - "+idx);
      throw new RuntimeException("not exist item idx");
    }

    return map;
  }

  public Long itemSave(ItemDto itemDto,User user) throws RuntimeException {
    try {
      Member member = memberRepository.findById(user.getUsername()).orElseThrow(()->new UsernameNotFoundException("no member"));

      Item item = new Item();

      item.setName(itemDto.getName());
      item.setDescription(itemDto.getDescription());
      item.setCode(itemDto.getCode());
      item.setPrice(itemDto.getPrice());
      item.setQuantity(itemDto.getQuantity());
      item.setState(itemDto.getState());
      item.setMember(member);

      return itemRepository.save(item).getIdx();
    } catch (UsernameNotFoundException e) {
      log.error("itemService - no member id");
      throw new UsernameNotFoundException("no member id");
    } catch (RuntimeException e) {
      log.error("itemService - save error");
      throw new RuntimeException("item save error");
    }
  }

  public Long itemModify(ItemDto itemDto, Long idx) throws RuntimeException {
    try {
      Item item = null;

      if (idx == null) {
        item = new Item();
      } else {
        item = itemRepository.findByIdx(idx);
      }

      item.setName(itemDto.getName());
      item.setDescription(itemDto.getDescription());
      item.setCode(itemDto.getCode());
      item.setPrice(itemDto.getPrice());
      item.setQuantity(itemDto.getQuantity());
      item.setState(itemDto.getState());

      return itemRepository.save(item).getIdx();
    } catch (RuntimeException e) {
      log.error("save error");
      throw new RuntimeException("item save error");
    }
  }

  public boolean itemDelete(User user,Long idx) {
    try {
      Item item = itemRepository.findByIdx(idx);

      if (Objects.equals(item.getMember().getId(), user.getUsername())) {
        itemImgRepository.deleteByItem(item);
        itemRepository.delete(item);

        return true;
      } else {
        throw new UsernameNotFoundException("not correct");
      }
    } catch (UsernameNotFoundException e) {
      log.error("itemDelete error : not coincide user id");
      return false;
    } catch (RuntimeException e) {
      log.error("itemDelete error");
      return false;
    }
  }
}
