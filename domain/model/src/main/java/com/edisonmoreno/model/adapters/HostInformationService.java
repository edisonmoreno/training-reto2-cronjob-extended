package com.edisonmoreno.model.adapters;

import com.edisonmoreno.model.adapters.dto.CronExecutionDTO;
import reactor.core.publisher.Mono;

public interface HostInformationService {
    Mono<CronExecutionDTO> call(String host);
}
