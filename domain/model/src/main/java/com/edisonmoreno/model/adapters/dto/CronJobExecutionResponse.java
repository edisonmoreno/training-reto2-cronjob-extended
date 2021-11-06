package com.edisonmoreno.model.adapters.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CronJobExecutionResponse implements Serializable {
    private String aggregateId;
    private String typeName;
    private String state;
    private String message;
}
