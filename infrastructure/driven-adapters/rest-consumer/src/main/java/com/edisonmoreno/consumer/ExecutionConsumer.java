package com.edisonmoreno.consumer;

import com.edisonmoreno.model.adapters.ExecutionConsumerService;
import com.edisonmoreno.model.adapters.dto.CronExecutionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ExecutionConsumer implements ExecutionConsumerService {
    WebClient webClient;

    @Override
    public Mono<CronExecutionDTO> getExecutionInformation(String baseHost) {
        this.webClient = WebClient.create(baseHost);
        webClient = WebClient.builder()
                .baseUrl(baseHost)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return webClient.get()
                .exchange()
                .map(response -> CronExecutionDTO.builder()
                        .statusCode(response.statusCode().toString())
                        .body(response.body((inputMessage, context) -> inputMessage.getBody().toString()))
                        .header(response.headers().asHttpHeaders().toSingleValueMap())
                        .build());
    }
}
