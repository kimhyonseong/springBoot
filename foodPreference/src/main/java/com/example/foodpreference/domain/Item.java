package com.example.foodpreference.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicInsert
@DynamicUpdate
public class Item extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idx;

  @Comment("분류")
  private String code;
  private String name;
  private String description;
  @Comment("정상 : 10, 비노출 : 90")
  @ColumnDefault("10")
  private int state;
  private int price;
  private int quantity;

  @ManyToOne
  @JoinColumn(name = "member_idx")
  @JsonBackReference
  @ToString.Exclude
  private Member member;
}
