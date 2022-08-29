package com.example.foodlist.domain;

import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Data
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Food extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private String name;

    @Comment("나라 코드")
    private int countryCode;

    @Comment("음식 나라")
    private String country;

    @Comment("노출여부")
    private boolean display;

    @Comment("0:기본, 10:New, 20:Best, 30:Hot")
    private int state;

    @ManyToOne
    @JoinColumn(name = "category_code")
    @ToString.Exclude
    private FoodCategory foodCategory;

    @OneToOne
    @JoinColumn(name = "idx")
    private FoodImg foodImg;
}