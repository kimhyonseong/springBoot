package com.example.foodpreference.service;

import com.example.foodpreference.domain.Member;
import com.example.foodpreference.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;
//    private final PasswordEncoder passwordEncoder;

    @Value("${spring.security.user.name}")
    private String testId;
    @Value("${spring.security.user.password}")
    private String testPassword;


    // 로그인 시 이놈이 요청을 가로챔
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = null;
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if (username.equals(testId)) {
            member = new Member();
            member.setId(testId);
            member.setPassword(bCryptPasswordEncoder.encode(testPassword));
            member.setRole("ADMIN");
        } else {

            member = memberRepository.findById(username);

            // data.sql 데이터는 평문으로 들어가서 암호화 해주어야함
            if (username.equals("lss1545")) {
                member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
            }
        }

        System.out.println("username = "+username);
        System.out.println("member = "+member);

        if (member == null) throw new UsernameNotFoundException("없는 아이디 입니다.");

        return member;
    }

    @Transactional
    public int signUp(Member member) {
        try {
            if(checkMemberIdDuplication(member.getId())) {
                return 300;
            }
            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
            member.setPassword(bcrypt.encode(member.getPassword()));

            memberRepository.save(member);
            return 200;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return 400;
        }
    }

    @Transactional(readOnly = true)
    public boolean checkMemberIdDuplication(String id) {
        return memberRepository.existsById(id);
    }
}
