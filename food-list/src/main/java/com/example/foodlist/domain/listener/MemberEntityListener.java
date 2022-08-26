package com.example.foodlist.domain.listener;

import com.example.foodlist.domain.Member;
import com.example.foodlist.domain.MemberHistory;
import com.example.foodlist.domain.MemberLogin;
import com.example.foodlist.repository.MemberHistoryRepository;
import com.example.foodlist.repository.MemberLoginRepository;
import com.example.foodlist.support.BeanUtils;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreUpdate;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

public class MemberEntityListener {
    @PostPersist
    @PreUpdate
    public void memberHistory(Object o) {
        MemberHistoryRepository memberHistoryRepository = BeanUtils.getBean(MemberHistoryRepository.class);
        Member member = (Member) o;
        MemberHistory memberHistory = new MemberHistory();

        memberHistory.setMember(member);
        memberHistory.setName(member.getName());
        memberHistory.setMemberId(member.getMemberId());

        if (member.getState() == null) {
            // 가입
            memberHistory.setState(10);
        } else if (member.getState() != 10) {
            memberHistory.setState(member.getState());
        } else {
            // 개인 정보 변경
            memberHistory.setState(20);
        }

        memberHistoryRepository.save(memberHistory);
    }

    @PostUpdate
    public void memberLogin(Object o) {
        MemberLoginRepository memberLoginRepository = BeanUtils.getBean(MemberLoginRepository.class);
        Member member = (Member) o;

        if (member.getLastLoginDate() != null) {
            MemberLogin memberLogin = new MemberLogin();

            memberLogin.setMemberId(member.getMemberId());
            memberLogin.setLoginDate(member.getLastLoginDate());
            memberLogin.setMember(member);
            memberLogin.setWorkDate(LocalDateTime.now());
            //memberLogin.setIp();

            memberLoginRepository.save(memberLogin);
        }
    }
}
