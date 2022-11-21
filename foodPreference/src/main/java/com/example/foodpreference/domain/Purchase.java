package com.example.foodpreference.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Purchase extends BaseEntity{
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idx;

  @Comment("배송 받는 사람 이름")
  private String addressee;

  @Comment("배송지")
  private String memberAddress;

  @Comment("배송 중, 배송준비 중, 배송완료, 배송 취소")
  private String orderState;

  @Comment("배송비")
  private int deliverCost;

  @ManyToOne
  @ToString.Exclude
  @JsonBackReference
  @JoinColumn(name = "member_idx")
  private Member member;

  @OneToMany
  @ToString.Exclude
  @JsonManagedReference
  @JoinColumn(name = "idx")
  private List<PurchaseItem> orderItemList = new ArrayList<>();
}
