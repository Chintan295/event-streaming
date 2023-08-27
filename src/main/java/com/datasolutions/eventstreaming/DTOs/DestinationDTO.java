package com.datasolutions.eventstreaming.DTOs;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Data
@Builder
public class DestinationDTO {
    private String destinationId;
    private String endPoint;
    private Date cursor;
    private long retryCount;
    private long retryThreshold;
}
