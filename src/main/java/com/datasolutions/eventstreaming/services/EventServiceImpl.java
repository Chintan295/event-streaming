package com.datasolutions.eventstreaming.services;

import com.datasolutions.eventstreaming.DTOs.DestinationConfigDTO;
import com.datasolutions.eventstreaming.DTOs.EventDTO;
import com.datasolutions.eventstreaming.config.LoggerController;
import com.datasolutions.eventstreaming.controllers.request.EventSendRequest;
import com.datasolutions.eventstreaming.controllers.response.EventAckResponse;
import com.datasolutions.eventstreaming.entities.DestinationConfig;
import com.datasolutions.eventstreaming.mapper.EventMapper;
import com.datasolutions.eventstreaming.repositories.DestinationConfigRepository;
import com.datasolutions.eventstreaming.repositories.EventRepository;
import com.datasolutions.eventstreaming.repositories.SourceDestinationMapRepository;
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
    private final DestinationConfigRepository destinationConfigRepository;
    private final SourceDestinationMapRepository sourceDestinationMapRepository;

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
    public Mono<EventDTO> getNextEventByDestination(DestinationConfigDTO destinationConfigDTO) {
        return Mono.just(eventRepository.findFirstByCreatedDateAfter(destinationConfigDTO.getCursor()))
                .flatMap(event -> event.map(Mono::just).orElseGet(Mono::empty))
                .map(EventMapper::mapToEventDTO);
    }

    public void increaseRetryCountOrDiscard(EventDTO eventDTO, DestinationConfig destinationConfig) {
        destinationConfig.setRetryCount(destinationConfig.getRetryCount() + 1);
        if (destinationConfig.getRetryCount() >= destinationConfig.getRetryThreshold()) {
            destinationConfig.setRetryCount(0);
            destinationConfig.setCursor(eventDTO.getCreatedDate());
            destinationConfigRepository.save(destinationConfig);
            return;
        }
        destinationConfigRepository.save(destinationConfig);
    }

    @Override
    public Mono<String> sendEventToDestination(EventDTO eventDTO, DestinationConfig destinationConfig) {

        long isValid = sourceDestinationMapRepository.countBySourceIdAndDestinationId(
                eventDTO.getSourceId(),
                destinationConfig.getDestinationId());

        if(isValid == 0) {
            destinationConfig.setCursor(eventDTO.getCreatedDate());
            destinationConfigRepository.save(destinationConfig);
            return Mono.empty();
        }

        LoggerController.logger.info("Sending event= " + eventDTO.toString() + "to url=" + destinationConfig.getEndPoint());
        WebClient webClient = WebClient.builder()
                .baseUrl(destinationConfig.getEndPoint())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        return webClient.post()
                .bodyValue(eventDTO)
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> {
                    LoggerController.logger.info("Error in sending event to destination = " + destinationConfig.getDestinationId());
                    increaseRetryCountOrDiscard(eventDTO, destinationConfig);
                    return Mono.empty();
                })
                .onStatus(HttpStatus::is2xxSuccessful, clientResponse -> {
                    LoggerController.logger.info("Event sent successfully");
                    destinationConfig.setRetryCount(0);
                    destinationConfig.setCursor(eventDTO.getCreatedDate());
                    destinationConfigRepository.save(destinationConfig);
                    return Mono.empty();
                })
                .bodyToMono(String.class)
                .onErrorResume(error -> {
                    LoggerController.logger.info("Error" + error.getLocalizedMessage());
                    increaseRetryCountOrDiscard(eventDTO, destinationConfig);
                    return Mono.empty();
                });
    }
}
