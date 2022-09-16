package com.example.foodlist.domain;

import com.example.foodlist.annotation.NoSpecial;
import com.example.foodlist.domain.listener.MemberEntityListener;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicInsert
@DynamicUpdate
@EntityListeners(value = {MemberEntityListener.class})
public class Member extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @NotBlank
    @Comment(value = "사용자 이름")
    @Column(length = 50, nullable = false)
    private String name;

    @NoSpecial
    @Comment(value = "사용자 아이디")
    @Column(length = 50, nullable = false, unique = true)
    @JsonProperty("member_id")
    private String memberId;

    @NotEmpty
    @Comment("사용자 비밀번호")
    @Column(length = 255, nullable = false)
    @JsonProperty("member_pw")
    private String memberPw;

    @Comment("사용자 상태 - 10:정상, 30:로그인 정지, 40:탈퇴, 50:휴면")
    @ColumnDefault("10")
    private Integer state;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "idx",insertable = false, updatable = false)
    @ToString.Exclude
    private List<MemberHistory> memberHistories = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "idx",insertable = false, updatable = false)
    @ToString.Exclude
    private List<MemberLogin> memberLogins = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "idx")
    @ToString.Exclude
    private MemberLastLogin memberLastLogin;

    @OneToMany
    @JoinColumn(name = "idx")
    @ToString.Exclude
    @JsonManagedReference
    private List<Review> reviews = new ArrayList<>();
}
