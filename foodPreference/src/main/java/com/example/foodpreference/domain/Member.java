package com.example.foodpreference.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(indexes = {
        @Index(name = "idx_unique_id", columnList = "id", unique = true)
})
@DynamicInsert
@DynamicUpdate
public class Member extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String name;

    @Column(unique = true)
    private String id;
    private String password;

    @ColumnDefault("10")
    private Integer state;

    @ColumnDefault("'user'")
    private String role;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "member_idx")
    @JsonManagedReference
    @ToString.Exclude
    private List<Item> items;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "member_idx")
    @JsonManagedReference
    @ToString.Exclude
    private List<Cart> cart;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> map = new HashSet<>();
        map.add(new SimpleGrantedAuthority(this.getRole()));

        return map;
    }

    @Override
    public String getUsername() {
        return this.getName();
    }

    // 만료 여부 확인
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠겼는지 확인
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    // 패스워드 만료 확인
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 사용 가능 여부
    @Override
    public boolean isEnabled() {
        return this.getState() == 10;
    }
}
