package com.redmath.studentapp.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableAsync
@EnableScheduling
@EnableCaching
@Configuration
public class CacheConfiguration {
    @Async
    public void sendEmail(){
        //send auto generated email to user
    }

    @Scheduled( fixedDelay = 2000)
    public void reconcile(){
        //generate a scheduled report of sales
    }
}
