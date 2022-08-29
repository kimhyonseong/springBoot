package com.example.foodlist.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FoodCategory extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryName;
    private int categoryCode;

    @OneToMany
    @JoinColumn(name = "category_code")
    @ToString.Exclude
    private List<Food> foodList = new ArrayList<>();
}
