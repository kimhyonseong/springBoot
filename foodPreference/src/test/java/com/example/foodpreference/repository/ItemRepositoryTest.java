package com.example.foodpreference.repository;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.Member;
import com.example.foodpreference.dto.ItemJoinImg;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

@SpringBootTest
class ItemRepositoryTest {
  @Autowired
  private ItemRepository itemRepository;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private ItemImgRepository itemImgRepository;

  @Test
  void findByMember() {
    Member member = new Member();
    member.setName("user1");
    member.setId("user1");
    member.setPassword("1234");
    memberRepository.save(member);
    memberRepository.flush();
    System.out.println("member save");

    Member findMember = memberRepository.findByName("user1");
    System.out.println(member);
    System.out.println("member findByName");

    Item item = new Item();
    item.setMember(findMember);
    item.setName("test1");
    itemRepository.save(item);
    itemRepository.flush();
    System.out.println("item save");

    List<Item> saveItem = itemRepository.findByMember(findMember);
    System.out.println(saveItem.get(0));
    System.out.println("item findByIdx");

    System.out.println("findByMember-------------------------------------");
    List<Item> newItem4 = itemRepository.findByMember(findMember);
    System.out.println(newItem4);
  }

  @Test
  void findAllByMember() {
    List<Item> itemList = null;
    Member member = null;

    try {
      member = memberRepository.findById("admin").orElseThrow(() ->new UsernameNotFoundException("no member"));
      itemList = itemRepository.findAllByMember(member);
      System.out.println(itemList);
      System.out.println(itemList.get(0));
    } catch (UsernameNotFoundException e) {
      System.out.println("findAdminItem error : no member");
    } catch (Exception e) {
      System.out.println("findAdminItem Runtime exception");
    }
  }

  @Test
  void joinTest() {
    try {
      List<ItemJoinImg> list = itemRepository.itemJoinItemImg(1L);
      System.out.println(list.get(0));
      //System.out.println(list.get(0).getFileName());  // null
      //System.out.println(list.get(0).getItem().getName());  //NullPointerException
      //System.out.println(list.get(0).getItemImg().getImgPath());  //NullPointerException
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
  }
}