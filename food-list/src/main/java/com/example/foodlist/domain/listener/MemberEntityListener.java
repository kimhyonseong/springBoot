package com.example.foodlist.domain.listener;

import com.example.foodlist.domain.Member;
import com.example.foodlist.domain.MemberHistory;
import com.example.foodlist.repository.MemberHistoryRepository;
import com.example.foodlist.support.BeanUtils;

import javax.persistence.PostPersist;
import javax.persistence.PreUpdate;

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

        System.out.println(member);
        memberHistoryRepository.save(memberHistory);
    }
}
