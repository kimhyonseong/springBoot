package com.example.foodlist.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Comment("10:노출, 90:비노출")
    private int display;

    @Comment("0:기본, 10:New, 20:Best, 30:Hot")
    private int state;

    @ManyToOne
    @JoinColumn(name = "category_code")
    @ToString.Exclude
    private FoodCategory foodCategory;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idx")
    @ToString.Exclude
    @JsonManagedReference
    private FoodImg foodImg;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "food_idx")
    @ToString.Exclude
    @JsonManagedReference
    private List<Review> reviews = new ArrayList<>();
}