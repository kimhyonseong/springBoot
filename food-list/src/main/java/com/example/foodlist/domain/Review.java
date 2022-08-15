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
    private int score;

    private String comment;

    @ManyToOne(optional = false)
    @ToString.Exclude
    private Food food;

    @ManyToOne(optional = false)
    @ToString.Exclude
    private Member member;
}
