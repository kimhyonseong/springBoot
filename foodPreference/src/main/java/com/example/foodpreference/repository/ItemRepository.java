package com.example.foodpreference.repository;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.Member;
import com.example.foodpreference.dto.ItemJoinImgInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

  List<Item> findAllByMember(Member member, Pageable pageable);
  List<Item> findAllByMember(Member member);
  List<Item> findByMember(Member member);

  @Query(value = "SELECT i.idx AS item_idx " +
          ",img.idx AS img_idx FROM item AS i " +
          "LEFT JOIN item_img AS img ON i.idx=img.item_idx " +
          "WHERE i.member_idx = ?1 " +
          "limit ?2,?3"
          , nativeQuery = true)
  List<ItemJoinImgInterface> itemJoinItemImg(Long memberIdx,int start,int size);
}
