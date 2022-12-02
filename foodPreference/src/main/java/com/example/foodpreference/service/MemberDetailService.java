package com.example.foodpreference.service;

import com.example.foodpreference.domain.Member;
import com.example.foodpreference.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Value("${spring.security.user.name}")
    private String testId;
    @Value("${spring.security.user.password}")
    private String testPassword;
    @Value("${spring.security.user.roles}")
    private String testRoles;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = null;
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if (username.equals(testId)) {
            member = new Member();
            member.setId(testId);
            member.setPassword(bCryptPasswordEncoder.encode(testPassword));
            member.setRole(testRoles);
        } else {
            member = memberRepository.findById(username).orElseThrow(()->
            new UsernameNotFoundException("없는 아이디 입니다."));
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(member.getRole()));

        return new User(member.getId(),member.getPassword(),authorities);
    }
}
