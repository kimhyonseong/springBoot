package com.example.foodlist.scheduler;

import com.example.foodlist.repository.MemberRepository;
import com.example.foodlist.service.MemberLastLoginService;
import com.example.foodlist.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberScheduler {
    private final MemberLastLoginService lastLoginService;
    private final MemberService memberService;

    @Scheduled(cron = "0 0 6 * * *")
    public void changeDormancyMember() {
        lastLoginService.changeDormancyMember();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteMember() {
        memberService.deleteSecessionMember();
    }
}
