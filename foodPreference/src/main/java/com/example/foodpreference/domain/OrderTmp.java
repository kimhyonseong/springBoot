package com.example.foodpreference.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

// 결제 페이지로 들어갔을때 물건 갯수만큼 미리 빼놓기
// Item -> OrderTmp -> OrderItem
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderTmp extends BaseEntity{
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idx;

  @ManyToOne
  @JoinColumn(name = "item_idx")
  @ToString.Exclude
  private Item item;

  @ManyToOne
  @JoinColumn(name = "member_idx")
  @ToString.Exclude
  private Member member;

  private int amount;
}
