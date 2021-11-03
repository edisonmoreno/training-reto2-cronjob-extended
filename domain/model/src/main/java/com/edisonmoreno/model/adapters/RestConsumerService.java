package com.edisonmoreno.model.adapters;

import com.edisonmoreno.model.adapters.dto.CronJobExecutionResponse;
import com.edisonmoreno.model.adapters.dto.CronExecutionDTO;
import com.edisonmoreno.model.adapters.dto.CronJobResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RestConsumerService {
    Flux<CronJobResponse> getCronJobList();

    Mono<CronJobExecutionResponse> sendData(CronExecutionDTO cronExecutionDTO);
}
