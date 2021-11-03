package com.edisonmoreno.model.adapters.dto;

import lombok.*;

import java.time.Instant;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Execution {
    private String state;
    private long duration;
    private Instant date;
}
