package com.edisonmoreno.model.adapters.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class CronJobExecutionRequest {
    private String type;
    private String cronJobId;
    private String state;
    private String duration;
    private String date;

}
