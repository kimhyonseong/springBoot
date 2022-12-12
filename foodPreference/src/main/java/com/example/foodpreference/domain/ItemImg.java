package com.example.foodpreference.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@DynamicInsert
@DynamicUpdate
public class ItemImg extends BaseEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idx;
  private String imgPath;
  private String fileName;
  private String originFileName;
  private Integer size;
  private String extension;

  @OneToOne(cascade = CascadeType.REMOVE)
  @JoinColumn(name = "item_idx")
  @JsonBackReference
  @ToString.Exclude
  private Item item;
}
