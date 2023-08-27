package com.datasolutions.eventstreaming.config;

import com.datasolutions.eventstreaming.entities.DestinationConfig;
import com.datasolutions.eventstreaming.repositories.DestinationConfigRepository;
import com.datasolutions.eventstreaming.schedulers.DeliveryHandler;
import com.datasolutions.eventstreaming.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class DeliveryThreadManager implements ApplicationRunner {

    private final DestinationConfigRepository destinationConfigRepository;
    private final EventService eventService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LoggerController.logger.info("DeliveryThreadManager initiated");
        List<DestinationConfig> destinationConfigList = new ArrayList<>();
        destinationConfigRepository.findAll().forEach(destinationConfigList::add);

        if(destinationConfigList.size() > 0) {
            ExecutorService executorService = Executors.newFixedThreadPool(destinationConfigList.size());

            for (int i = 0; i < destinationConfigList.size(); i++) {
                LoggerController.logger.info("Creating thread for destination=" + destinationConfigList.get(i));
                Runnable job = DeliveryHandler.builder()
                        .eventService(eventService)
                        .destinationConfig(destinationConfigList.get(i))
                        .build();
                executorService.execute(job);
            }
        }
    }
}
