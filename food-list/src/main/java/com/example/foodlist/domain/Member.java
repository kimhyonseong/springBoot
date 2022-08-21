package com.example.foodlist.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
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

    @Comment("사용자 비밀번호")
    @Column(length = 255, nullable = false)
    private String memberPw;

    @Comment("사용자 상태 - 10:정상, 20:로그인 정지, 30:휴면, 90:탈퇴")
    @ColumnDefault("10")
    private int state;
}
