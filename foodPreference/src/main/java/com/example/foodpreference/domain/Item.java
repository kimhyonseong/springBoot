package com.example.foodpreference.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Item extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idx;

  @Comment("분류")
  private String code;
  private String name;
  private String description;
  private int price;
  private int quantity;

  @ManyToOne
  @JoinColumn(name = "member_idx")
  @ToString.Exclude
  private Member member;

  @OneToMany
  @JoinColumn(name = "item_idx")
  @ToString.Exclude
  private List<ItemImg> imgs;

  @OneToMany
  @JoinColumn(name = "item_idx")
  @ToString.Exclude
  private List<Cart> carts;
}
