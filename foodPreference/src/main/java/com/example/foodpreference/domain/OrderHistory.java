package com.example.foodpreference.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
// 구매내역
public class OrderHistory extends BaseEntity{
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idx;

  @Comment("배송 받는 사람 이름")
  private String addressee;

  @Comment("배송지")
  private String memberAddress;

  @Comment("배송 준비 중 : 10, 배송 중 : 20, 배송완료 : 30, 배송 취소 : 90")
  @ColumnDefault("'10'")
  private int orderState;

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
  @JoinColumn(name = "order_idx")
  private List<OrderItem> orderItemList = new ArrayList<>();
}
