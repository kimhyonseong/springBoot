package com.example.foodlist.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicInsert
@DynamicUpdate
public class Review extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private Integer score;

    private String comment;

    @Column(updatable = false)
    private String memberId;

    @Comment("10 : 정상, 20 : 비노출 및 정지, 30 : 삭제")
    @ColumnDefault("10")
    private Integer state;

    @ManyToOne(optional = false)
    @JoinColumn(name = "food_idx")
    @ToString.Exclude
    @JsonBackReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Food food;

    @ManyToOne(optional = false)
    @JoinColumn(name = "member_idx")
    @ToString.Exclude
    @JsonBackReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;
}
