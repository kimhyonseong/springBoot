package com.example.foodpreference.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ItemImg extends BaseEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idx;
  private String imgUrl;
  private int width;
  private int height;
  private String extension;

  @ManyToOne
  //@JoinColumn(name = "item_idx")
  private Item item;
}
