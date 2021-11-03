package com.edisonmoreno.cron.quartz;

import com.edisonmoreno.infra.email.EmailService;
import com.edisonmoreno.model.EmailBody;
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

    private void sendEmail(String Email) {
        assert Email != null;
//        EmailService emailService = new EmailService();
//        EmailBody emailBody = EmailBody.builder()
//                .subject("PRUEBA")
//                .content("JOB")
//                .email(Email)
//                .build();
//        emailService.sendEmail(emailBody);
    }

    public void updateExecution(String cronJobId, JobDataMap data) {

//        executionUseCase.getExecutionInformation(data.get("url").toString())
//                .map(executionDTO -> executionDTO.toBuilder()
//                        .type("ms-commands.cronjob.execution")
//                        .cronJobId(cronJobId)
//                        .state("SUCCESS")
//                        .duration("20")
//                        .build())
//                .flatMap(cronJobUseCase::sendData)
//                .subscribe();

    }

}
