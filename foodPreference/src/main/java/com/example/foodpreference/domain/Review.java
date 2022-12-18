package com.example.foodpreference.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@DynamicInsert
@DynamicUpdate
public class Review extends BaseEntity{
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int idx;
  private int score;
  private String comment;

  @ManyToOne
  @JoinColumn(name = "member_idx")
  @JsonBackReference
  @ToString.Exclude
  private Member member;

  @ManyToMany
  @JoinColumn(name = "item_idx")
  @JsonBackReference
  @ToString.Exclude
  private List<Item> item = new ArrayList<>();
}
