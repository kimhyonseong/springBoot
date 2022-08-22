package com.example.foodlist.service;

import com.example.foodlist.domain.Member;
import com.example.foodlist.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.AnnotationTypeMismatchException;
import java.sql.SQLException;
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

    public int put(Member newMember) {
        int count = countId(newMember.getMemberId());

        switch (count) {
            case -1 :  // 에러
                return -1;
            case 0 :  // 존재하지 않는 아이디 - 진행
                try {
                    memberRepository.save(newMember);
                    return 0;
                } catch (AnnotationTypeMismatchException e) {
                    e.printStackTrace();
                    return -2;  //에러 발생
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                    return -1;  //에러 발생
                }
            default:  // 이미 존재하는 아이디
                return 1;
        }
    }

    private int countId(String memberId) {
        List<Member> count = null;

        try {
//            if (!memberIdTest(memberId)) {
//                throw new RuntimeException("memberId pattern is wrong");
//            }
            count = memberRepository.findByMemberId(memberId);

            return count.size();
        } catch (RuntimeException e) {
            return -1;
        }
    }

    private Boolean memberIdTest(String memberId) {
        Pattern pattern = Pattern.compile("^[\\da-zA-Z]+$");

        try {
            if (memberId == null || memberId.equals("")) {
                throw new Exception("id is empty");
            }
            Matcher matcher = pattern.matcher(memberId);
            return matcher.matches();
        } catch (Exception e) {
            return false;
        }
    }
}
