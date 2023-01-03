package com.example.foodpreference.scheduler;

import com.example.foodpreference.repository.OrderTmpRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableAsync
@Component
@Slf4j
@RequiredArgsConstructor
public class TmpOrderScheduler {
  private final OrderTmpRepository orderTmpRepository;

  @Async
  @Scheduled(cron = "0 * * * * *")
  public void deleteTmpOrder() {
    orderTmpRepository.deleteTmpOrderByTime(1);
  }
}
