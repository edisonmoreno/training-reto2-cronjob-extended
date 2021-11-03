package com.edisonmoreno.usecase.execution;

import com.edisonmoreno.model.adapters.ExecutionConsumerService;
import com.edisonmoreno.model.adapters.dto.CronExecutionDTO;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ExecutionUseCase {

    private final ExecutionConsumerService executionConsumerService;

    public Mono<CronExecutionDTO> getExecutionInformation(String baseHost) {
        return executionConsumerService.getExecutionInformation(baseHost);
    }
}
