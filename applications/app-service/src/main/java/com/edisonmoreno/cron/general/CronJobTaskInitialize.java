package com.edisonmoreno.cron.general;

import com.edisonmoreno.cron.quartz.QuartzSchedule;
import com.edisonmoreno.model.adapters.dto.CronJobResponse;
import com.edisonmoreno.usecase.cronjob.CronJobUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CronJobTaskInitialize implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(CronJobTaskInitialize.class);
    private final QuartzSchedule quartzSchedule;
    private final CronJobUseCase cronJobUseCase;
    private int repeatCount = 1;

    public CronJobTaskInitialize(CronJobUseCase cronJobUseCase) {
        this.cronJobUseCase = cronJobUseCase;
        this.quartzSchedule = new QuartzSchedule(cronJobUseCase);
    }

    @Override
    public void run() {
        logger.info("initialize schedule...");
        quartzSchedule.init();
        if (repeatCount > 3) {
            logger.info("exceeded the number of repetitions...");
            //Todo: Cambiar por parametro de finalización de ejecución, o número de repeticiones
            return;
        }
        logger.info("Update list cron job...#{}", repeatCount);
        List<CronJobResponse> cronJobList = cronJobUseCase.getCronJobList().collectList().block();
        logger.info("add tasks...");
        Objects.requireNonNull(cronJobList)
                .forEach(objectResponse -> {
                    Map<String, String> dataTask = createDataTask(objectResponse);
                    quartzSchedule.addTask(objectResponse.getId(), objectResponse.getName(), objectResponse.getCronExpression(), dataTask);
                });
        repeatCount++;
    }

    private Map<String, String> createDataTask(CronJobResponse objectResponse) {
        Map<String, String> dataTask = new HashMap<>();
        dataTask.put("url", objectResponse.getUrl());
        dataTask.put("email", objectResponse.getEmail());
        return dataTask;
    }
}
