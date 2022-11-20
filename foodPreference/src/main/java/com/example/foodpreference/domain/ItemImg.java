package com.example.foodpreference.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ItemImg extends BaseEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idx;
  private String imgPath;
  private String imgUrl;
  private String originName;
  private Integer size;
  private String extension;

  @ManyToOne
  @JsonBackReference
  private Item item;
}
