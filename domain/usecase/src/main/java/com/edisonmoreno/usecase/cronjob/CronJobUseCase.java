package com.edisonmoreno.usecase.cronjob;

import com.edisonmoreno.model.adapters.CronJobService;
import com.edisonmoreno.model.adapters.HostInformationService;
import com.edisonmoreno.model.adapters.dto.CronJobExecutionResponse;
import com.edisonmoreno.model.adapters.dto.CronJobResponse;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CronJobUseCase {
    private final CronJobService cronJobService;
    private final HostInformationService hostInformationService;

    public Flux<CronJobResponse> getCronJobList() {
        return cronJobService.getCronJobList();
    }

    public Mono<CronJobExecutionResponse> getHostInformation(String cronJobId, String host) {
        return hostInformationService.call(host)
                .doOnNext(cronExecutionDTO -> System.out.println(cronExecutionDTO.toString()))
                .map(executionDTO -> executionDTO.toBuilder()
                        .type("ms-commands.cronjob.execution")
                        .cronJobId(cronJobId)
                        .build())
                .flatMap(cronJobService::sendCronJobExecution);
    }
}
