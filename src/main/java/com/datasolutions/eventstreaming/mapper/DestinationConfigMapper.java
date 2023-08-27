package com.datasolutions.eventstreaming.mapper;

import com.datasolutions.eventstreaming.DTOs.DestinationConfigDTO;
import com.datasolutions.eventstreaming.entities.DestinationConfig;

public class DestinationConfigMapper {

    public static DestinationConfig mapToDestinationConfig(DestinationConfigDTO destinationConfigDTO) {
        return DestinationConfig.builder()
                .destinationId(destinationConfigDTO.getDestinationId())
                .endPoint(destinationConfigDTO.getEndPoint())
                .retryCount(destinationConfigDTO.getRetryCount())
                .cursor(destinationConfigDTO.getCursor())
                .retryTimeout(destinationConfigDTO.getRetryTimeout())
                .build();
    }

    public static DestinationConfigDTO mapToDestinationConfigDTO(DestinationConfig destinationConfig) {
        return DestinationConfigDTO.builder()
                .destinationId(destinationConfig.getDestinationId())
                .endPoint(destinationConfig.getEndPoint())
                .retryCount(destinationConfig.getRetryCount())
                .cursor(destinationConfig.getCursor())
                .retryTimeout(destinationConfig.getRetryTimeout())
                .build();
    }
}
