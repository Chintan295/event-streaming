package com.datasolutions.eventstreaming.schedulers;

import com.datasolutions.eventstreaming.config.LoggerController;
import com.datasolutions.eventstreaming.entities.Destination;
import com.datasolutions.eventstreaming.repositories.DestinationRepository;
import com.datasolutions.eventstreaming.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.datasolutions.eventstreaming.mapper.DestinationMapper.mapToDestinationDTO;

@Component
@RequiredArgsConstructor
public class Destination3 {
    private final EventService eventService;
    private final DestinationRepository destinationRepository;

    @Value("${com.event-streaming.schedulers.destination3.id}")
    private String destinationId;
    private Optional<Destination> destination;

    @Scheduled(fixedRateString = "${com.event-streaming.schedulers.destination3.retry-timeout}")
    public void processNextEvent() {
        LoggerController.logger.info("Processing event for destination=" + destinationId);
        destination = destinationRepository.findByDestinationId(destinationId);
        destination.ifPresent(value -> eventService.getNextEventByDestination(mapToDestinationDTO(value))
                .flatMap(eventDTO -> eventService.sendEventToDestination(eventDTO, destination.get()))
                .subscribe());
    }
}
