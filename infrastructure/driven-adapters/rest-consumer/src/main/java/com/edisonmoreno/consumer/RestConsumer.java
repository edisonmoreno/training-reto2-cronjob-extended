package com.edisonmoreno.consumer;

import com.edisonmoreno.model.adapters.RestConsumerService;
import com.edisonmoreno.model.adapters.dto.CronExecutionDTO;
import com.edisonmoreno.model.adapters.dto.CronJobExecutionRequest;
import com.edisonmoreno.model.adapters.dto.CronJobExecutionResponse;
import com.edisonmoreno.model.adapters.dto.CronJobResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RestConsumer implements RestConsumerService {
    private final WebClient client;

    public Flux<CronJobResponse> getCronJobList() {
        return client
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/cronjob/list")
                        .build())
                .retrieve()
                .bodyToFlux(CronJobResponse.class);
    }

    @Override
    public Mono<CronJobExecutionResponse> sendData(CronExecutionDTO cronExecutionDTO) {
        CronJobExecutionRequest request = CronJobExecutionRequest.builder()
                .type(cronExecutionDTO.getType())
                .cronJobId(cronExecutionDTO.getCronJobId())
                .state(cronExecutionDTO.getStatusCode())
                .duration(cronExecutionDTO.getDuration())
                .date(Instant.now().toString())
                .build();

        return client.post()
                .body(Mono.just(request), CronJobExecutionRequest.class)
                .retrieve()
                .bodyToMono(CronJobExecutionResponse.class);
    }
}