package com.datasolutions.eventstreaming.controllers.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SourceDestinationMapRequest {
    private final String sourceId;
    private final String destinationId;
}
