package com.example.foodpreference.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(indexes = {
        @Index(name = "idx_unique_id", columnList = "id", unique = true)
})
public class Member extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String name;

    private String id;
    private String password;
    private String role;

    @OneToMany
    @JoinColumn(name = "member_idx")
    @ToString.Exclude
    private List<Item> items;

    @OneToMany
    @JoinColumn(name = "member_idx")
    @ToString.Exclude
    private List<Cart> cart;
}
