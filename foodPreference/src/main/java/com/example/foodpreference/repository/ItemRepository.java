package com.example.foodpreference.repository;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {
  Item findByIdx(Long idx);
  List<Item> findAll();
  List<Item> findAllByState(int state);
  List<Item> findAllByCode(String code);
  Page<Item> findAll(Pageable pageable);
  Page<Item> findAllByStateIs(Pageable pageable, int state);
  Page<Item> findAllByCode(String code, Pageable pageable);
  Page<Item> findAllByCodeAndStateIs(String code,int state, Pageable pageable);

  @Query("select i,img from Item i left join i.itemImg img")
  List<Object> findByMember(Member member);
}
