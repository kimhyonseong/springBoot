package com.example.foodlist.scheduler;

import com.example.foodlist.service.MemberLastLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberScheduler {
    private final MemberLastLoginService lastLoginService;
    @Scheduled(cron = "0 0 6 * * *")
    public void changeDormancyMember() {
        lastLoginService.changeDormancyMember();
    }
}
