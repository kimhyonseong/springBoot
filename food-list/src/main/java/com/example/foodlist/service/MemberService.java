package com.example.foodlist.service;

import com.example.foodlist.domain.Member;
import com.example.foodlist.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.lang.annotation.AnnotationTypeMismatchException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member login(String loginId, String loginPw, HttpServletResponse response) throws Exception {
        try {
            Member member = memberRepository.findByMemberIdAndMemberPw(loginId,loginPw);

            if (member == null ||
                    (!Objects.equals(member.getMemberId(), loginId) ||
                     !Objects.equals(member.getMemberPw(), loginPw))) {
                return null;
            } else {
                return member;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException();
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
            count = memberRepository.findAllByMemberId(memberId);
            return count.size();
        } catch (RuntimeException e) {
            return -1;
        }
    }
}
