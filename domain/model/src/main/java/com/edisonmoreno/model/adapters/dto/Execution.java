package com.edisonmoreno.model.adapters.dto;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Execution implements Serializable {
    private String state;
    private long duration;
    private Instant date;
}
