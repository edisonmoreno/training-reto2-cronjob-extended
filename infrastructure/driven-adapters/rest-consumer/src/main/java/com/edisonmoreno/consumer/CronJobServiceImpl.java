package com.edisonmoreno.consumer;

import com.edisonmoreno.model.adapters.CronJobService;
import com.edisonmoreno.model.adapters.dto.CronExecutionDTO;
import com.edisonmoreno.model.adapters.dto.CronJobExecutionRequest;
import com.edisonmoreno.model.adapters.dto.CronJobExecutionResponse;
import com.edisonmoreno.model.adapters.dto.CronJobResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Slf4j
@Service
public class CronJobServiceImpl implements CronJobService {
    @Autowired
    @Qualifier("webClientQueries")
    WebClient clientQueries;
    @Autowired
    @Qualifier("webClientCommands")
    WebClient clientCommands;

    public Flux<CronJobResponse> getCronJobList() {
        return clientQueries.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/cronjob/list")
                        .build())
                .retrieve()
                .bodyToFlux(CronJobResponse.class);
    }

    @Override
    public Mono<CronJobExecutionResponse> sendCronJobExecution(CronExecutionDTO cronExecutionDTO) {
        CronJobExecutionRequest request = CronJobExecutionRequest.builder()
                .type(cronExecutionDTO.getType())
                .cronJobId(cronExecutionDTO.getCronJobId())
                .state(cronExecutionDTO.getState())
                .duration(cronExecutionDTO.getDuration())
                .date(Instant.now().toString())
                .httpCode(cronExecutionDTO.getHttpCode())
                .build();

        return clientCommands.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/cronjob/execution")
                        .build())
                .body(Mono.just(request), CronJobExecutionRequest.class)
                .retrieve()
                .bodyToMono(CronJobExecutionResponse.class)
                .doOnSuccess(response -> log.info("*** response: {}", response.toString()))
                .doOnError(error -> log.info("=== error in sendCronJobExecution: {}", error.getMessage()));
    }
}