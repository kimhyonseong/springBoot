package com.example.foodpreference.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PurchaseItem {
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
  @JoinColumn(name = "order_idx")
  @JsonBackReference
  private Purchase order;
}
