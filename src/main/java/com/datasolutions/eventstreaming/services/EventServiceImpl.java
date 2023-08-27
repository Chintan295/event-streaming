package com.datasolutions.eventstreaming.services;

import com.datasolutions.eventstreaming.DTOs.DestinationDTO;
import com.datasolutions.eventstreaming.DTOs.EventDTO;
import com.datasolutions.eventstreaming.config.LoggerController;
import com.datasolutions.eventstreaming.controllers.request.EventSendRequest;
import com.datasolutions.eventstreaming.controllers.response.EventAckResponse;
import com.datasolutions.eventstreaming.entities.Destination;
import com.datasolutions.eventstreaming.mapper.EventMapper;
import com.datasolutions.eventstreaming.repositories.DestinationRepository;
import com.datasolutions.eventstreaming.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.datasolutions.eventstreaming.mapper.EventMapper.mapToEvent;
import static com.datasolutions.eventstreaming.mapper.EventMapper.mapToEventAckResponse;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final DestinationRepository destinationRepository;

    @Override
    public Mono<EventAckResponse> saveEvent(EventSendRequest eventSendRequest) {
        return Mono.just(eventSendRequest)
                .map(event -> eventRepository.save(mapToEvent(event)))
                .map(event -> mapToEventAckResponse(event))
                .onErrorMap(throwable -> {
                    LoggerController.logger.info("Error in storing event");
                    throw new RuntimeException();
                });
    }

    @Override
    public Mono<EventDTO> getNextEventByDestination(DestinationDTO destinationDTO) {
        LoggerController.logger.info("Getting next event after " + destinationDTO.getCursor());
        return Mono.just(eventRepository.findFirstByCreatedDateAfter(destinationDTO.getCursor()))
                .flatMap(event -> event.map(Mono::just).orElseGet(Mono::empty))
                .map(EventMapper::mapToEventDTO);
    }

    public void increaseRetryCountOrDiscard(EventDTO eventDTO, Destination destination) {
        destination.setRetryCount(destination.getRetryCount() + 1);
        if (destination.getRetryCount() >= destination.getRetryThreshold()) {
            destination.setRetryCount(0);
            destination.setCursor(eventDTO.getCreatedDate());
        }
        destinationRepository.save(destination);
    }

    @Override
    public Mono<String> sendEventToDestination(EventDTO eventDTO, Destination destination) {
        LoggerController.logger.info("Sending event= " + eventDTO.toString() + "to url=" + destination.getEndPoint());
        WebClient webClient = WebClient.builder()
                .baseUrl(destination.getEndPoint())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        return webClient.post()
                .bodyValue(eventDTO)
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> {
                    LoggerController.logger.info("Error in sending event to destination = " + destination.getDestinationId());
                    increaseRetryCountOrDiscard(eventDTO, destination);
                    return Mono.empty();
                })
                .onStatus(HttpStatus::is2xxSuccessful, clientResponse -> {
                    LoggerController.logger.info("Event sent successfully");
                    destination.setCursor(eventDTO.getCreatedDate());
                    destinationRepository.save(destination);
                    return Mono.empty();
                })
                .bodyToMono(String.class)
                .onErrorResume(error -> {
                    LoggerController.logger.info("Error" + error.getLocalizedMessage());
                    increaseRetryCountOrDiscard(eventDTO, destination);
                    return Mono.empty();
                });
    }
}
