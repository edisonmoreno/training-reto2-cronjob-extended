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
public class CronExecutionDTO implements Serializable {
    private String type;
    private String cronJobId;
    private String state;
    private String duration;
    private String date;
    private String httpCode;
}
