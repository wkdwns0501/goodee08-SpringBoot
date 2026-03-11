package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LogApplication implements ApplicationRunner {

    @Value("${loopCount}")
    private Integer loopCount;

    @Value("${testerName}")
    private String testerName;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("loopCount: " + loopCount);
        log.info("testerName: " + testerName);
        log.trace("application started");
        log.debug("application started");
        log.info("application started");
        log.warn("application started");
        log.error("application started");
    }
}
