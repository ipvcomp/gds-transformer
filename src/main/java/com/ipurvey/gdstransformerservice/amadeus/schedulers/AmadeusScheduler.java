package com.ipurvey.gdstransformerservice.amadeus.schedulers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AmadeusScheduler {

    @Scheduled(cron = "${amadeus.scheduler.cron.expression}")
    public void runScheduler() {
        //TODO : make to bookings
        log.info("Scheduler executed at the start of every minute");
    }

}
