package com.datasolutions.eventstreaming.services;

import com.datasolutions.eventstreaming.DTOs.DestinationDTO;
import com.datasolutions.eventstreaming.DTOs.EventDTO;
import com.datasolutions.eventstreaming.controllers.request.EventSendRequest;
import com.datasolutions.eventstreaming.controllers.response.EventAckResponse;
import com.datasolutions.eventstreaming.entities.Destination;
import reactor.core.publisher.Mono;


public interface EventService {
    Mono<EventAckResponse> saveEvent(EventSendRequest eventSendRequest);

    Mono<EventDTO> getNextEventByDestination(DestinationDTO destinationDTO);

    Mono<String> sendEventToDestination(EventDTO eventDTO, Destination destination);
}
