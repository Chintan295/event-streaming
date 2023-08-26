package com.datasolutions.eventstreaming.entities;

import lombok.Builder;

@Builder
public class Destination extends BaseEntity {
    private String destinationId;
    private String destinationEndPoint;

    public String getDestinationId() {
        return destinationId;
    }

    public String getDestinationEndPoint() {
        return destinationEndPoint;
    }

    public void setDestinationEndPoint(String destinationEndPoint) {
        this.destinationEndPoint = destinationEndPoint;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }
}
