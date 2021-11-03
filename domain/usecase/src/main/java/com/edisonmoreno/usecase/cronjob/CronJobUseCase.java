package com.edisonmoreno.usecase.cronjob;

import com.edisonmoreno.model.adapters.RestConsumerService;
import com.edisonmoreno.model.adapters.dto.CronJobExecutionResponse;
import com.edisonmoreno.model.adapters.dto.CronExecutionDTO;
import com.edisonmoreno.model.adapters.dto.CronJobResponse;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CronJobUseCase {
    private final RestConsumerService restConsumerService;

    public Flux<CronJobResponse> getCronJobList() {
        return restConsumerService.getCronJobList();
    }

    public Mono<CronJobExecutionResponse> sendData(CronExecutionDTO cronExecutionDTO) {
        return restConsumerService.sendData(cronExecutionDTO);
    }
}
