package com.datasolutions.eventstreaming.mapper;

import com.datasolutions.eventstreaming.DTOs.DestinationDTO;
import com.datasolutions.eventstreaming.entities.Destination;

public class DestinationMapper {

    public static Destination mapToDestination(DestinationDTO destinationDTO) {
        return Destination.builder()
                .destinationId(destinationDTO.getDestinationId())
                .endPoint(destinationDTO.getEndPoint())
                .retryCount(destinationDTO.getRetryCount())
                .cursor(destinationDTO.getCursor())
                .build();
    }

    public static DestinationDTO mapToDestinationDTO(Destination destination) {
        return DestinationDTO.builder()
                .destinationId(destination.getDestinationId())
                .endPoint(destination.getEndPoint())
                .retryCount(destination.getRetryCount())
                .cursor(destination.getCursor())
                .build();
    }
}
