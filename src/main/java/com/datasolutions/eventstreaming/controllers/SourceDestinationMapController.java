package com.datasolutions.eventstreaming.controllers;

import com.datasolutions.eventstreaming.DTOs.DestinationConfigDTO;
import com.datasolutions.eventstreaming.controllers.request.SourceDestinationMapRequest;
import com.datasolutions.eventstreaming.controllers.response.Response;
import com.datasolutions.eventstreaming.entities.SourceDestinationMap;
import com.datasolutions.eventstreaming.repositories.SourceDestinationMapRepository;
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
@RequestMapping("/api/source-destination")
public class SourceDestinationMapController {

    private final SourceDestinationMapRepository sourceDestinationMapRepository;

    @PostMapping("/add")
    public Mono<Response<SourceDestinationMap>> addDestination(@RequestBody SourceDestinationMapRequest sourceDestinationMapRequest) {

        return Mono.just(sourceDestinationMapRepository.save(SourceDestinationMap.builder()
                        .sourceId(sourceDestinationMapRequest.getSourceId())
                        .destinationId(sourceDestinationMapRequest.getDestinationId())
                        .build()))
                .map(sourceDestinationMap1 -> Response.<SourceDestinationMap>builder()
                        .status(HttpStatus.OK)
                        .data(sourceDestinationMap1)
                        .build())
                .subscribeOn(Schedulers.boundedElastic());
    }
}
