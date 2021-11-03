package com.edisonmoreno.cron.general;

import com.edisonmoreno.cron.quartz.QuartzSchedule;
import com.edisonmoreno.model.adapters.dto.CronJobResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CronJobTaskInitialize implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(CronJobTaskInitialize.class);
    private final QuartzSchedule quartzSchedule;
    private final List<CronJobResponse> cronJobList;

    public CronJobTaskInitialize(List<CronJobResponse> cronJobList) {
        this.cronJobList = cronJobList;
        this.quartzSchedule = new QuartzSchedule();
    }

    @Override
    public void run() {
        logger.info("Update: list cron job...");

        quartzSchedule.init();
        cronJobList.forEach(objectResponse -> {
            Map<String, String> dataTask = new HashMap<>();
            dataTask.put("url", objectResponse.getUrl());
            dataTask.put("email", objectResponse.getEmail());
            quartzSchedule.addTask(objectResponse.getId(), objectResponse.getName(), objectResponse.getCronExpression(), dataTask);
        });
    }
}
