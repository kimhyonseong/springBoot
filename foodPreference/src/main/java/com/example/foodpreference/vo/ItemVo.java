package com.example.foodpreference.vo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

@Getter
@Setter
public class ItemVo {
  private Long idx;
  private String code;
  private String name;
  private String description;
  private int state;
  private int price;
  private int quantity;
}
