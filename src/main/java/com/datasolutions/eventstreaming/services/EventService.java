package com.datasolutions.eventstreaming.services;

import com.datasolutions.eventstreaming.DTOs.DestinationConfigDTO;
import com.datasolutions.eventstreaming.DTOs.EventDTO;
import com.datasolutions.eventstreaming.controllers.request.EventSendRequest;
import com.datasolutions.eventstreaming.controllers.response.EventAckResponse;
import com.datasolutions.eventstreaming.entities.DestinationConfig;
import reactor.core.publisher.Mono;


public interface EventService {
    Mono<EventAckResponse> saveEvent(EventSendRequest eventSendRequest);

    Mono<EventDTO> getNextEventByDestination(DestinationConfigDTO destinationConfigDTO);

    Mono<String> sendEventToDestination(EventDTO eventDTO, DestinationConfig destinationConfig);
}
