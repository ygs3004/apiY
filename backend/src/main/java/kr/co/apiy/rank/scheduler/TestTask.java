package kr.co.apiy.rank.scheduler;

import kr.co.apiy.global.utils.ApiRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Log4j2
@RequiredArgsConstructor
public class TestTask {
    // 초 분 시간 일 월 요일
    // 매일 자정에 실행: 0 0 0 * * ?
    // 매일 오전 9시 실행: 0 0 9 * * ?
    // 매주 월요일 오전 9시 실행: 0 0 9 ? * MON
    // 매월 1일 자정 실행: 0 0 0 1 * ?
    // 매시간 30분마다 실행: 0 30 * * * ?

    private final ApiRequest apiRequest;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    // @Scheduled(fixedRate = 5000)
    // public void scheduleTaskWithFixedRate() {
    //     log.info("Fixed Rate Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
    // }
    //
    // @Scheduled(cron = "0 * * * * ?")
    // public void scheduleTaskWithCronExpression() {
    //     log.info("Cron Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
    // }
    //
    // public void getInfo() {
    //
    // }
}
