package com.example.foodlist.service;

import com.example.foodlist.domain.Member;
import com.example.foodlist.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.AnnotationTypeMismatchException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public int login(String loginId, String loginPw) {
        try {
            List<Member> member = memberRepository.findByMemberId(loginId);
            System.out.println(member.get(0));

            if (member.get(0) == null) {
                throw new RuntimeException("no member");
            } else if (member.get(0).getState() != 10) {
                throw new RuntimeException("제한된 사용자입니다.");
            } else if (!Objects.equals(member.get(0).getMemberPw(), loginPw)) {
                throw new RuntimeException("no match password");
            }
        } catch (RuntimeException e) {
            return -1;
        }
        return 1;
    }

    public int put(Member member) {
        int count = this.countId(member.getMemberId());
        Member newMember = null;
        List<Member> list = new ArrayList<>();

        try {
            newMember = memberRepository.save(member);
            list.add(newMember);
        } catch (RuntimeException e) {
            return -1;
        }

        return list.size();
    }

    public int countId(String memberId) {
        List<Member> count = null;

        try {
            count = memberRepository.findByMemberId(memberId);
            return count.size();
        } catch (RuntimeException e) {
            return -1;
        }
    }
}
