package com.edisonmoreno.consumer;

import com.edisonmoreno.model.adapters.HostInformationService;
import com.edisonmoreno.model.adapters.dto.CronExecutionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class HostInformationServiceImpl implements HostInformationService {
    WebClient webClient;

    @Override
    public Mono<CronExecutionDTO> call(String host) {
        this.webClient = WebClient.create(host);
        webClient = WebClient.builder()
                .baseUrl(host)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return webClient.get()
                .retrieve()
                .toEntity(String.class)
                .elapsed()
                .map(tuple -> CronExecutionDTO.builder()
                        .duration(tuple.getT1().toString())
                        .date(Instant.now().toString())
                        .httpCode(tuple.getT2().getStatusCode().toString())
                        .state(tuple.getT2().getStatusCode().is2xxSuccessful() ? "SUCCESS" : "FAILED")
                        .build())
                .doOnError(System.out::println);
    }
}
