package com.example.foodpreference.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
// 구매 내역의 구매 상품
public class PurchaseItem extends BaseEntity{
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idx;

  @Comment("주문 수량")
  private int itemCount;

  @Comment("가격")
  private int itemPrice;

  @ManyToOne
  @ToString.Exclude
  @JoinColumn(name = "item_idx")
  @JsonBackReference
  private Item item;

  @ManyToOne
  @ToString.Exclude
  @JoinColumn(name = "purchase_idx")
  @JsonBackReference
  private Purchase purchase;
}
