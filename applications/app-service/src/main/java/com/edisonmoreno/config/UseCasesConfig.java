package com.edisonmoreno.config;

import com.edisonmoreno.model.adapters.ExecutionConsumerService;
import com.edisonmoreno.model.adapters.dto.CronExecutionDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import reactor.core.publisher.Mono;

@Configuration
@ComponentScan(basePackages = "com.edisonmoreno.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)
public class UseCasesConfig {

}
