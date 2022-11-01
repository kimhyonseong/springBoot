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
  private Long cart_idx;

  @ManyToMany
  private List<Item> items;
}
