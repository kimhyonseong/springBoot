package com.example.foodpreference.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Cart {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idx;
  private int quantity;

  @ManyToOne
  @JoinColumn(name = "member_idx")
  private Member member;

  @ManyToOne
  @JoinColumn(name = "item_idx")
  private Item item;
}
