package com.edisonmoreno.cron.general;


import com.edisonmoreno.model.adapters.dto.CronJobResponse;
import com.edisonmoreno.usecase.cronjob.CronJobUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;


@Component
public class CronJobTaskScheduler {
    private static Logger logger = LoggerFactory.getLogger(CronJobTaskScheduler.class);
    @Autowired
    private TaskScheduler taskScheduler;
    private final CronJobUseCase cronJobUseCase;

    public CronJobTaskScheduler(CronJobUseCase cronJobUseCase) {
        this.cronJobUseCase = cronJobUseCase;
    }

    @PostConstruct
    public void schedule() {
        List<CronJobResponse> cronJobList = cronJobUseCase.getCronJobList().collectList().block();
        taskScheduler.schedule(new CronJobTaskInitialize(cronJobList), new CronTrigger("0/15 * * * * ?"));
    }
}