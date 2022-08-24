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
            Member member = memberRepository.findByMemberIdAndMemberPw(loginId,loginPw);

            if (member == null ||
                    (!Objects.equals(member.getMemberId(), loginId) ||
                     !Objects.equals(member.getMemberPw(), loginPw))) {
                return 0;
            } else {
                return member.getState();
            }
        } catch (RuntimeException e) {
            return -1;
        }
    }

    public int put(Member member) {
        Member newMember;
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
        List<Member> count;

        try {
            count = memberRepository.findByMemberId(memberId);
            return count.size();
        } catch (RuntimeException e) {
            return -1;
        }
    }
}
