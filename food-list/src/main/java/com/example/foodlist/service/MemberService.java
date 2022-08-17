package com.example.foodlist.service;

import com.example.foodlist.domain.Member;
import com.example.foodlist.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public int put(Member newMember) {
        int result = 100;  // 정상
        int count = countId(newMember.getMemberId());

        switch (count) {
            case -1 :  // 에러
                return 400;
            case 0 :  // 존재하지 않는 아이디 - 진행
                break;
            default:  // 이미 존재하는 아이디
                return 200;
        }

        try {
            memberRepository.save(newMember);
        } catch (RuntimeException e) {
            return 400;  //에러 발생
        }

        return result;
    }

    private int countId(String memberId) {
        List<Member> count = null;

        try {
            if (!memberIdTest(memberId)) {
                throw new RuntimeException("memberId pattern is wrong");
            }
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
