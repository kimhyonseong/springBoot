package com.example.foodpreference.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@ToString
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
  @JsonBackReference
  @ToString.Exclude
  private Member member;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "item_idx")
  @JsonManagedReference
  @ToString.Exclude
  private List<ItemImg> imgs;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "item_idx")
  @JsonBackReference
  @ToString.Exclude
  private List<Cart> carts;
}
