package com.datasolutions.eventstreaming.schedulers;


import com.datasolutions.eventstreaming.config.LoggerController;
import com.datasolutions.eventstreaming.entities.DestinationConfig;
import com.datasolutions.eventstreaming.repositories.DestinationConfigRepository;
import com.datasolutions.eventstreaming.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class EventCleanupScheduler {

    private final EventRepository eventRepository;
    private final DestinationConfigRepository destinationConfigRepository;

    @Scheduled(fixedRateString = "${com.event-streaming.schedulers.event-cleanup}")
    public void cleanEvents() {
        LoggerController.logger.info("Cleaning up delivered and discarded events");
        Date minimumDate = destinationConfigRepository.findMinimumDate();

        LoggerController.logger.info("Delivered or discarded upto " + minimumDate);
        eventRepository.deleteEventsBeforeDate(minimumDate);
    }
}
