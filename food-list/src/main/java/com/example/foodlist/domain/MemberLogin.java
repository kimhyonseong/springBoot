package com.example.foodlist.domain;

import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@DynamicInsert
public class MemberLogin extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Comment("멤버 아이디")
    private String memberId;

    @Column(name = "loginDate", columnDefinition = "datetime(6) default now(6) comment '로그인 시간'")
    @CreatedDate
    private LocalDateTime loginDate;

    @Comment("로그인 아이피")
    private String ip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    @ToString.Exclude
    private Member member;
}
