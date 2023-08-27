package com.datasolutions.eventstreaming.schedulers;

import com.datasolutions.eventstreaming.entities.DestinationConfig;
import com.datasolutions.eventstreaming.services.EventService;
import lombok.Builder;
import lombok.Data;

import static com.datasolutions.eventstreaming.mapper.DestinationConfigMapper.mapToDestinationConfigDTO;

@Data
@Builder
public class DeliveryHandler implements Runnable {

    private EventService eventService;
    private DestinationConfig destinationConfig;

    @Override
    public void run() {
        while (true) {
            eventService.getNextEventByDestination(mapToDestinationConfigDTO(destinationConfig))
                    .flatMap(eventDTO -> eventService.sendEventToDestination(eventDTO, destinationConfig))
                    .block();
            if (destinationConfig.getRetryCount() > 0) {
                try {
                    Thread.sleep(destinationConfig.getRetryTimeout());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
