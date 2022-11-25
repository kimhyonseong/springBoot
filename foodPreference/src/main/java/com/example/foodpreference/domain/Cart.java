package com.example.foodpreference.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Cart extends BaseEntity{
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idx;
  private int amount;

  @ManyToOne
  @JoinColumn(name = "member_idx")
  @JsonBackReference
  private Member member;

  @ManyToOne
  @JoinColumn(name = "item_idx")
  @JsonManagedReference
  private Item item;
}
