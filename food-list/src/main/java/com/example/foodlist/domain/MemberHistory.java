package com.example.foodlist.domain;

import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicInsert
public class MemberHistory extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String name;

    @Column(nullable = false)
    private String memberId;

    @Comment("10:회원가입, 20:개인정보 변경, 30:로그인정지 전환, 40:일반탈퇴, 50:휴면계정 전환")
    private Integer state;

    @ManyToOne
    @JoinColumn(name = "member_idx")
    @ToString.Exclude
    private Member member;
}
