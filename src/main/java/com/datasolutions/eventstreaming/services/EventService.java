package com.datasolutions.eventstreaming.services;

import com.datasolutions.eventstreaming.DTOs.DestinationDTO;
import com.datasolutions.eventstreaming.DTOs.EventDTO;
import com.datasolutions.eventstreaming.controllers.request.EventSendRequest;
import com.datasolutions.eventstreaming.entities.Destination;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface EventService {
    Mono<Boolean> saveEvent(EventSendRequest eventSendRequest);

    Mono<EventDTO> getNextEventByDestination(DestinationDTO destinationDTO);

    Mono<String> sendEventToDestination(EventDTO eventDTO, Destination destination);
}
