package com.example.foodlist.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    private String name;

    private String imgUrl;

    private Long size;

    @OneToOne
    @JoinColumn(name = "food_idx")
    @ToString.Exclude
    @JsonBackReference
    private Food food;
}
