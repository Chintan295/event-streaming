package com.datasolutions.eventstreaming.controllers;

import com.datasolutions.eventstreaming.DTOs.DestinationConfigDTO;
import com.datasolutions.eventstreaming.config.LoggerController;
import com.datasolutions.eventstreaming.controllers.response.Response;
import com.datasolutions.eventstreaming.repositories.DestinationConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Date;

import static com.datasolutions.eventstreaming.mapper.DestinationConfigMapper.mapToDestinationConfig;
import static com.datasolutions.eventstreaming.mapper.DestinationConfigMapper.mapToDestinationConfigDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/destination-config")
public class DestinationConfigController {

    private final DestinationConfigRepository destinationConfigRepository;

    @PostMapping("/add")
    public Mono<Response<DestinationConfigDTO>> addDestination(@RequestBody DestinationConfigDTO destinationConfigDTO) {
        destinationConfigDTO.setCursor(new Date());
        LoggerController.logger.info(mapToDestinationConfig(destinationConfigDTO).toString());
        return Mono.just(destinationConfigRepository.save(mapToDestinationConfig(destinationConfigDTO)))
                .map(destinationConfig -> Response.<DestinationConfigDTO>builder()
                        .status(HttpStatus.OK)
                        .data(mapToDestinationConfigDTO(destinationConfig))
                        .build())
                .subscribeOn(Schedulers.boundedElastic());
    }

}
