package com.example.foodlist.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Review extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer score;

    private String comment;

    @ManyToOne(optional = false)
    @JoinColumn(name = "food_idx")
    @ToString.Exclude
    private Food food;

    @ManyToOne(optional = false)
    @JoinColumn(name = "member_idx")
    @ToString.Exclude
    private Member member;
}
