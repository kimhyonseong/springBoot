package com.example.foodlist.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FoodImg extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String imgName;

    private int size;

    private String extension;

    @OneToOne
    @JoinColumn(name = "food_idx")
    private Food food;
}
