package com.edisonmoreno.model.adapters.dto;

import lombok.*;

import java.util.Map;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CronExecutionDTO {
    private String type;
    private String cronJobId;
    private String state;
    private String duration;
    private String date;

    private String statusCode;
    private String body;
    private Map<String, String> header;
}
