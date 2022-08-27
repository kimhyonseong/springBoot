package com.example.foodlist.scheduler;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
public class MemberScheduler {
    @Async
    public void changeDormancyMember() {
        
    }
}
