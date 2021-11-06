package com.edisonmoreno.cron.quartz;

import com.edisonmoreno.infra.email.EmailServiceImpl;
import com.edisonmoreno.model.EmailBody;
import com.edisonmoreno.usecase.cronjob.CronJobUseCase;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class QuartzListener implements JobListener {
    private static final Logger logger = LoggerFactory.getLogger(QuartzListener.class);
    private final CronJobUseCase cronJobUseCase;

    public QuartzListener(CronJobUseCase cronJobUseCase) {
        logger.info("QuartzListener()");
        this.cronJobUseCase = cronJobUseCase;
    }

    @Override
    public String getName() {
        return "QuartzListener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        logger.info("QuartzListener.jobToBeExecuted()");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        logger.info("QuartzListener.jobExecutionVetoed()");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        logger.info("QuartzListener.jobWasExecuted()");

        updateExecution(context.getJobDetail().getKey().getName(), context.getJobDetail().getJobDataMap());

        sendEmail(context.getJobDetail().getJobDataMap().get("email").toString());
    }

    private void sendEmail(String email) {
        EmailServiceImpl emailServiceImpl = new EmailServiceImpl();
        EmailBody emailBody = EmailBody.builder()
                .subject("Ejecución de job")
                .content("Se ha ejecutado el job programado")
                .email(email)
                .build();
        emailServiceImpl.sendEmail(emailBody);
    }

    public void updateExecution(String cronJobId, JobDataMap data) {
        cronJobUseCase.getHostInformation(cronJobId, data.get("url").toString())
                .subscribe();
    }

}
