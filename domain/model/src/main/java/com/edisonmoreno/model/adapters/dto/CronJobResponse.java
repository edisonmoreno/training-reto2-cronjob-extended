package com.edisonmoreno.model.adapters.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CronJobResponse implements Serializable {
    private String id;
    private String name;
    private String url;
    private String cronExpression;
    private String timeout;
    private String retry;
    private String email;
    private Set<Execution> executions;
}