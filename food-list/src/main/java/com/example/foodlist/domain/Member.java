package com.example.foodlist.domain;

import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Member extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment(value = "사용자 이름")
    @Column(length = 50, nullable = false)
    private String name;

    @Comment(value = "사용자 아이디")
    @Column(length = 50, nullable = false, unique = true)
    private String memberId;
}
