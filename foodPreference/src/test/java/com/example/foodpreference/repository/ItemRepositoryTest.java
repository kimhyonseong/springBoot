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

  @Test
  void findByMember() {
    Member member = new Member();
    member.setName("user1");
    member.setId("user1");
    member.setPassword("1234");

    memberRepository.save(member);

    Member findMember = memberRepository.findByName("user1");

    Item item = new Item();
    item.setIdx(1L);
    item.setMember(findMember);
    item.setName("test1");

    System.out.println(itemRepository.findByMember(findMember));
  }
}