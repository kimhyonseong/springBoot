package com.example.foodpreference.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
  @JsonBackReference
  private Member member;

  @ManyToOne
  @JoinColumn(name = "item_idx")
  @JsonManagedReference
  private Item item;
}
