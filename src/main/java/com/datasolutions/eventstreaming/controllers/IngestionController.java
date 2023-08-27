package com.datasolutions.eventstreaming.controllers;

import com.datasolutions.eventstreaming.config.LoggerController;
import com.datasolutions.eventstreaming.controllers.request.EventSendRequest;
import com.datasolutions.eventstreaming.controllers.response.Response;
import com.datasolutions.eventstreaming.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/event")
public class IngestionController {

    private final EventService eventService;

    @PostMapping("/send")
    public Mono<Response<Boolean>> sendEvent(@RequestBody EventSendRequest eventSendRequest) {
        LoggerController.logger.info("sendEvent POST request with eventSendRequest=" + eventSendRequest.toString());
        return eventService.saveEvent(eventSendRequest)
                .map(eventAck -> Response.<Boolean>builder()
                        .status(HttpStatus.OK)
                        .data(eventAck)
                        .build())
                .subscribeOn(Schedulers.boundedElastic());
    }

}
