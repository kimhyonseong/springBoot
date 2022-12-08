package com.example.foodpreference.repository;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.ItemImg;
import com.example.foodpreference.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    System.out.println("member save");

    Member findMember = memberRepository.findByName("user1");
    System.out.println("member findByName");

    Item item = new Item();
    item.setIdx(1L);
    item.setMember(findMember);
    item.setName("test1");
    itemRepository.save(item);
    System.out.println("item save");

    Item saveItem = itemRepository.findByIdx(1L);
    System.out.println("item findByIdx");

    ItemImg itemImg = new ItemImg();
    itemImg.setItem(saveItem);
    itemImg.setImgUrl("test.jpg");
    itemImg.setImgPath("/path/");
    itemImgRepository.save(itemImg);

//    System.out.println(itemRepository.findByMember(findMember.getIdx()).get(0).toString());
  }
}