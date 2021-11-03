package com.edisonmoreno.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public class EmailBody {
    private String content;
    private String email;
    private String subject;
}
