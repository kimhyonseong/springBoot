package com.example.foodpreference.service;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.ItemImg;
import com.example.foodpreference.domain.Member;
import com.example.foodpreference.dto.ItemDto;
import com.example.foodpreference.dto.ItemJoinImg;
import com.example.foodpreference.repository.ItemImgRepository;
import com.example.foodpreference.repository.ItemRepository;
import com.example.foodpreference.repository.MemberRepository;
import com.example.foodpreference.utils.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
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
      Item item = itemRepository.findByIdx(idx).orElseThrow(NullPointerException::new);
      ItemImg itemImg = itemImgRepository.findByItem(item).orElse(new ItemImg());

      map.put("item",item);
      map.put("itemIdx",item.getIdx());
      map.put("name",item.getName());
      map.put("code",item.getCode());
      map.put("description",item.getDescription());
      map.put("price",item.getPrice());
      map.put("quantity",item.getQuantity());
      map.put("state",item.getState());
      map.put("img",itemImg.getImgPath()+itemImg.getFileName());
    } catch (NullPointerException e) {
      log.error("존재하지 않는 idx - "+idx);
      throw new RuntimeException("not exist item idx");
    } catch (RuntimeException e) {
      log.error("find item error : "+idx);
      throw new RuntimeException("error");
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
      Item item = itemRepository.findByIdx(idx).orElseThrow(NullPointerException::new);

      item.setName(itemDto.getName());
      item.setDescription(itemDto.getDescription());
      item.setCode(itemDto.getCode());
      item.setPrice(itemDto.getPrice());
      item.setQuantity(itemDto.getQuantity());
      item.setState(itemDto.getState());

      return itemRepository.save(item).getIdx();
    } catch (NullPointerException e) {
      log.error("modify error");
      throw new RuntimeException("item modify error : item is null");
    } catch (RuntimeException e) {
      log.error("modify error");
      throw new RuntimeException("item modify error");
    }
  }

  public boolean itemDelete(User user,Long idx) {
    try {
      Item item = itemRepository.findByIdx(idx).orElseThrow(NullPointerException::new);

      if (Objects.equals(item.getMember().getId(), user.getUsername())) {
        itemImgRepository.deleteByItem(item);
        itemRepository.delete(item);

        return true;
      } else {
        throw new UsernameNotFoundException("not correct");
      }
    } catch (NullPointerException e) {
      log.error("itemDelete error : item is null");
      return false;
    } catch (UsernameNotFoundException e) {
      log.error("itemDelete error : not coincide user id");
      return false;
    } catch (RuntimeException e) {
      log.error("itemDelete error");
      return false;
    }
  }

  public Map<String,Object> showItemWithImg(Pageable pageable) {
    Map<String, Object> returnMap = new HashMap<>();
    List<ItemJoinImg> itemList = null;
    Map<String, Object> pageMap = new HashMap<>();

    try {
      itemList = itemRepository.itemJoinImg(pageable);
      Page page = new Page(itemRepository.countItemJoinImg(),pageable.getPageNumber());

      pageMap.put("start",page.getStart());
      pageMap.put("end",page.getEnd());
      pageMap.put("prev",page.isPrev());
      pageMap.put("next",page.isNext());
      pageMap.put("blockSize",page.getBlockSize());
      pageMap.put("link","/itemList");
      pageMap.put("currentPage",pageable.getPageNumber());
      pageMap.put("pageObj",page);

      returnMap.put("itemList",itemList);
      returnMap.put("page",pageMap);
    } catch (RuntimeException e) {
      log.error("itemJoinImgList error");
    }
    return returnMap;
  }
}
