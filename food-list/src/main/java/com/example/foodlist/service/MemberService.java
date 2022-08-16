package com.example.foodlist.service;

import com.example.foodlist.domain.Member;
import com.example.foodlist.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public int put(Member newMember) {
        int result = 100;  // 정상
        int countId = countId(newMember.getMemberId());

        switch (countId) {
            case -1 :  // 에러
                return 400;
            case 0 :  // 존재하지 않는 아이디
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

    public int countId(String memberId) {
        int result = 0;
        List<Member> count = null;

        try {
            if (memberId == null || memberId.equals("")) {
                throw new RuntimeException("null memberId");
            }
            count = memberRepository.findByMemberId(memberId);

            if (count.size() > 0) {
                result++;
            }
        } catch (RuntimeException e) {
            return -1;
        }
        return result;
    }

    public Boolean memberIdTest(String memberId) {
        //String pattern = "";
        Pattern pattern = Pattern.compile("[ !@#$%^&*(),.;'\"\\|<>\\-_=]");

        try {
            if (memberId == null || memberId.equals("")) {
                throw new Exception("id is empty");
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
