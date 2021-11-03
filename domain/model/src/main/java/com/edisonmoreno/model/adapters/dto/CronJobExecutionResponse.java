package com.edisonmoreno.model.adapters.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class CronJobExecutionResponse {
    private String aggregateId;
    private String typeName;
    private String state;
    private String message;
}
