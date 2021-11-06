package com.edisonmoreno.cron.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QuartzTask implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("execute: (id: {}, name: {})", context.getJobDetail().getKey().getName(), context.getJobDetail().getDescription());
    }


}
