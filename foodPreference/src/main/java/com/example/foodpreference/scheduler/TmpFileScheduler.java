package com.example.foodpreference.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;
import java.util.Date;

@EnableAsync
@Slf4j
@Component
public class TmpFileScheduler {
  @Async
  @Scheduled(fixedRate = 60000)
  //@Scheduled(cron = "0 0 0 * * *")
  public void tmpFileDelete() {
    String path = Paths.get("./images/tmp").toAbsolutePath().toString();
    File dir = new File(path);
    Date date = new Date();

    try {
      log.info("tmp file delete start.");
      File[] files = dir.listFiles();
      boolean deleteSuccess = false;

      assert files != null;
      for (File file : files) {
        // 1000L - 1초
        if(file.lastModified() < date.getTime()-(1000L*12*360)) {
          deleteSuccess = file.delete();

          log.info(file.getName()+" delete "+deleteSuccess);
        }
      }
      log.info("tmp file delete end.");
    } catch (Exception e) {
      log.error("scheduler error : tmp file delete");
    }
  }
}
