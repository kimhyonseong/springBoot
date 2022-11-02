package com.example.foodpreference.service;

import com.example.foodpreference.domain.Member;
import com.example.foodpreference.repository.MemberRepository;
import com.example.foodpreference.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Value("${spring.security.user.name}")
    private String testId;
    @Value("${spring.security.user.password}")
    private String testPassword;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = null;

        if (username.equals(testId)) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            member = new Member();
            member.setId(testId);
            member.setPassword(bCryptPasswordEncoder.encode(testPassword));
            member.setRole("ADMIN");
        } else {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            member = memberRepository.findById(username);

            // data.sql 데이터는 평문으로 들어가서 암호화 해주어야함
            if (username.equals("lss1545")) {
                member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
            }
        }

        System.out.println("username = "+username);
        System.out.println("member = "+member);

        if (member != null) {
            return new MemberDetails(member);
        }
        return null;
    }
}
