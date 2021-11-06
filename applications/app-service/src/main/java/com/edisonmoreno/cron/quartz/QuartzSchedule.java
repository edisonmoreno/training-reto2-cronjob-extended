package com.edisonmoreno.cron.quartz;

import com.edisonmoreno.usecase.cronjob.CronJobUseCase;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Component
public final class QuartzSchedule {
    private static Logger logger = LoggerFactory.getLogger(QuartzSchedule.class);
    private Scheduler scheduler;
    private final CronJobUseCase cronJobUseCase;

    public QuartzSchedule(CronJobUseCase cronJobUseCase) {
        this.cronJobUseCase = cronJobUseCase;
        SchedulerFactory scheduleFact = new StdSchedulerFactory();
        try {
            scheduler = scheduleFact.getScheduler();
        } catch (ParseException | SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        try {
            this.scheduler.clear();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void addTask(String id, String name, String cronExpression, Map<String, String> data) {
        logger.info("addTask: (id: {}, name: {})", id, name);
        try {
            // Job
            JobDetail jobDetail = JobBuilder.newJob(QuartzTask.class)
                    .withDescription(name)
                    .withIdentity(id, "groupTask")
                    .setJobData(new JobDataMap(data))
                    .storeDurably(true).build();

            // Trigger
            CronTriggerImpl trigger = new CronTriggerImpl();
            trigger.setName(id);
            trigger.setCronExpression(cronExpression);
            trigger.setDescription(name);

            // Listener
            JobListener jobListener = new QuartzListener(cronJobUseCase);
            Matcher<JobKey> matcher = KeyMatcher.keyEquals(jobDetail.getKey());

            scheduler.getListenerManager().addJobListener(jobListener, matcher);
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (ParseException | java.text.ParseException | SchedulerException e) {
            e.printStackTrace();
        }

    }
}
